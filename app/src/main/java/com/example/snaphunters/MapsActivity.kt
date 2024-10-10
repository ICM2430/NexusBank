package com.example.snaphunters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.snaphunters.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val bogota = LatLng(4.5180, -74.0617)
        mMap.addMarker(MarkerOptions().position(bogota).title("Marcador en bogotá"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota))
    }
}