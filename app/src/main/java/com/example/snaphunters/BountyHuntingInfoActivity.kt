package com.example.snaphunters

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityBountyHuntingInfoBinding

class BountyHuntingInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBountyHuntingInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBountyHuntingInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}