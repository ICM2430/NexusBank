package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener(){
            validateForm(binding.email.text.toString(), binding.password.text.toString())
            signIn(binding.email.text.toString(), binding.password.text.toString())
        }

        binding.txtRegistro.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val i = Intent(this, WelcomeActivity::class.java)
            i.putExtra("email", currentUser.email.toString())
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun signIn(email: String, password: String) {
        if (validEmailAddress(email) && password != null) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    val message = it.exception!!.message
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    binding.email.text.clear()
                    binding.password.text.clear()
                }
            }
        }

    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = false
        if (email.isEmpty()) {
            binding.email.setError("Required!")
        } else if (!validEmailAddress(email)) {
            binding.email.setError("Invalid email address")
        } else if (password.isEmpty()) {
            binding.password.setError("Required!")
        } else if (password.length < 6) {
            binding.password.setError("Password should be at least 6 characters long!")
        } else {
            valid = true
        }
        return valid
    }

    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(regex.toRegex())
    }

}

