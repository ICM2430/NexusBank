package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnContinue.setOnClickListener(){
            var intentMenu = Intent(baseContext, MenuActivity::class.java)
            startActivity(intentMenu)
        }


    }
}