package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la base de datos
        dbHelper = DatabaseHelper(this)

        binding.btnRegister.setOnClickListener {
            val nombre = binding.editTextText2.text.toString()
            val apellido = binding.editTextText3.text.toString()
            val usuario = binding.editTextText4.text.toString()
            val correo = binding.editTextText5.text.toString()
            val contrasena = binding.editTextTextPassword2.text.toString()
            val diaNacimiento = binding.editTextText6.text.toString()
            val mesNacimiento = binding.editTextText7.text.toString()
            val anoNacimiento = binding.editTextText8.text.toString()

            // Validación de campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Por favor ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show()
            } else if (contrasena.length < 8) {
                Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                // Verificar si el usuario o correo ya existen
                val userExists = dbHelper.checkUserOrEmailExists(usuario, correo)
                if (userExists) {
                    Toast.makeText(this, "El usuario o correo ya existen", Toast.LENGTH_SHORT).show()
                } else {
                    // Guardar el usuario en la base de datos
                    val result = dbHelper.addUser(nombre, apellido, usuario, correo, contrasena, "$diaNacimiento/$mesNacimiento/$anoNacimiento")
                    if (result > 0) {
                        Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                        val intentWelcome = Intent(this, WelcomeActivity::class.java)
                        startActivity(intentWelcome)
                    } else {
                        Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
