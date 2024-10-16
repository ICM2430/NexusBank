package com.example.snaphunters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityRegisterBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


        binding.btnRegister.setOnClickListener(){
            if(validateForm(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())){
                crearUsuario(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())
            }else{
                Toast.makeText(this, "Error en el formulario", Toast.LENGTH_SHORT).show()
            }



        }
    }

    private fun crearUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        val USERS = "users/"
                        val userData = User()
                        userData.name = binding.txtName.text.toString()
                        userData.lastname = binding.txtLastName.text.toString()
                        userData.username = binding.txtUsername.text.toString()
                        userData.email = binding.txtEmail.text.toString()
                        userData.location = LatLng(0.0, 0.0)

                        myRef = database.getReference(USERS + it.uid)
                        myRef.setValue(userData)
                        val key = myRef.push().key
                        myRef = database.getReference(USERS + key)
                        myRef.setValue(userData)

                        updateUI(user   )
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
