package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.friends -> {
                    val Intent = Intent(baseContext, FriendsActivity::class.java)
                    startActivity(Intent)
                    true
                }
                R.id.menu -> {
                    val Intent = Intent(baseContext, MenuActivity::class.java)
                    startActivity(Intent)
                    true
                }
                R.id.maps -> {
                    val Intent = Intent(baseContext, MapsActivity::class.java)
                    startActivity(Intent)
                    true
                }
                R.id.profile -> {
                    val Intent = Intent(baseContext, ProfileActivity::class.java)
                    startActivity(Intent)
                    true
                }

                else -> false
            }
        }

    }
}