package com.example.snaphunters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.btnRegister.setOnClickListener(){
            validateForm(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
            crearUsuario(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
        }
    }

    private fun crearUsuario(email: String, password: String){

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName("${binding.txtName.text} ${binding.txtLastName.text}")
                            .setPhotoUri(Uri.parse("path/to/pic")) // fake uri, use Firebase Storage
                            .build()
                        it.updateProfile(profileUpdates)
                        updateUI(it)
                    }
                } else {
                    Toast.makeText(this, "Authentication failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(regex.toRegex())
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = false
        if (email.isEmpty()) {
            binding.txtEmail.setError("Required!")
        } else if (!validEmailAddress(email)) {
            binding.txtEmail.setError("Invalid email address")
        } else if (password.isEmpty()) {
            binding.txtPassword.setError("Required!")
        } else if (password.length < 6) {
            binding.txtPassword.setError("Password should be at least 6 characters long!")
        } else if (binding.txtName.text.isEmpty()) {
            binding.txtName.setError("Required!")
        }else if (binding.txtLastName.text.isEmpty()) {
            binding.txtLastName.setError("Required!")
        }else {
            valid = true
        }
        return valid
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val i = Intent(this, WelcomeActivity::class.java)
            startActivity(i)
        }
    }



}
