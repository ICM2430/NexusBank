package com.example.snaphunters

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityProfileOtherUserBinding

class ProfileOtherUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileOtherUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileOtherUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}