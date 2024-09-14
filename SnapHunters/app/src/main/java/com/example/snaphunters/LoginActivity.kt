package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.snaphunters.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener(){
            var intentWelcome = Intent(baseContext, WelcomeActivity::class.java)
            startActivity(intentWelcome)
        }

        binding.txtRegistro.setOnClickListener(){
            var intentRegister = Intent(baseContext, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}