package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.selectedItemId = R.id.menu

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.friends -> {
                    val Intent = Intent(baseContext, FriendsActivity::class.java)
                    startActivity(Intent)
                    false
                }
                R.id.menu -> {
                    true
                }
                R.id.maps -> {
                    val Intent = Intent(baseContext, MapsActivity::class.java)
                    startActivity(Intent)
                    false
                }
                R.id.profile -> {
                    val Intent = Intent(baseContext, ProfileActivity::class.java)
                    startActivity(Intent)
                    false
                }

                else -> false
            }
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.barra_navegacion, menu)
        return true
    }
}