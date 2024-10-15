package com.example.snaphunters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.example.snaphunters.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fingerprintHelper: FingerprintHelper

    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference

    val Users = "users/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        fingerprintHelper = FingerprintHelper(this)

        // Verificar soporte de biometría
        checkBiometricSupport()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (validateForm(email, password)) {
                signIn(email, password)
            }
        }

        binding.txtRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.imageButtonFingerprint.setOnClickListener {
            // Solo permitir autenticación biométrica si el usuario ha iniciado sesión
            if (isUserLoggedIn()) {
                authenticateWithFingerprint()
            } else {
                showToast("Por favor, inicie sesión primero.")
            }
        }
    }

    private fun checkBiometricSupport() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // El dispositivo admite la autenticación biométrica
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showToast("Este dispositivo no tiene hardware biométrico")
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showToast("El hardware biométrico no está disponible")
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                showToast("No hay huellas digitales registradas")
            }
        }
    }

    private fun authenticateWithFingerprint() {
        fingerprintHelper.authenticate(object : FingerprintHelper.AuthenticationCallback {
            override fun onAuthenticationSuccess() {
                // Redirigir al usuario a la actividad de bienvenida
                startActivity(Intent(this@LoginActivity, WelcomeActivity::class.java))
                finish() // Finaliza la actividad de inicio de sesión
            }

            override fun onAuthenticationError(errorMessage: String) {
                showToast(errorMessage)
            }
        }) // Iniciar la autenticación
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        currentUser?.let {
            // Guardar la sesión y el correo electrónico
            val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("isLoggedIn", true)
                putString("userEmail", it.email) // Guardar el email
                apply()
            }

            // Iniciar la actividad Welcome
            val intent = Intent(this, WelcomeActivity::class.java).apply {
                putExtra("email", it.email.toString())
            }



            startActivity(intent)
            finish() // Finaliza la actividad de inicio de sesión
        }
    }

    override fun onStart() {
        super.onStart()
        if (isUserLoggedIn()) {
            updateUI(auth.currentUser)
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {


                updateUI(auth.currentUser)


            } else {
                val message = task.exception?.message ?: "Error desconocido"
                showToast(message)
                binding.email.text.clear()
                binding.password.text.clear()
            }
        }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            email.isEmpty() || !validEmailAddress(email) -> {
                showToast("Por favor, ingrese un correo electrónico válido.")
                false
            }
            password.isEmpty() -> {
                showToast("Por favor, ingrese una contraseña.")
                false
            }
            else -> true
        }
    }

    private fun validEmailAddress(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }
}
