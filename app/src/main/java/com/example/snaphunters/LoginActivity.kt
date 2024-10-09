package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DatabaseHelper  // Inicializa la base de datos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la base de datos
        dbHelper = DatabaseHelper(this)

        // Manejar el evento de clic en el botón de inicio de sesión
        binding.btnLogin.setOnClickListener {
            val usuario = binding.editTextText.text.toString()  // Campo del nombre de usuario
            val contrasena = binding.editTextTextPassword.text.toString()  // Campo de la contraseña

            // Validar que los campos no estén vacíos
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar si las credenciales son válidas
                val validCredentials = dbHelper.checkUserCredentials(usuario, contrasena)
                if (validCredentials) {
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    val intentWelcome = Intent(this, WelcomeActivity::class.java)
                    startActivity(intentWelcome)  // Navegar a la pantalla de bienvenida
                } else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navegar a la pantalla de registro
        binding.txtRegistro.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}
