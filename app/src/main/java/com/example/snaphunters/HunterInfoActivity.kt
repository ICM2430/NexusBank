package com.example.snaphunters

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityHunterInfoBinding

class HunterInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHunterInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHunterInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}