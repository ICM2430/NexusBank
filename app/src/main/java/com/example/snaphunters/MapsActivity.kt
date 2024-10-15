package com.example.snaphunters

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de la barra de navegación
        binding.bottomNavigationView.selectedItemId = R.id.maps
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.friends -> {
                    val intent = Intent(baseContext, FriendsActivity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.menu -> {
                    val intent = Intent(baseContext, MenuActivity::class.java)
                    startActivity(intent)
                    false
                }
                R.id.maps -> true
                R.id.profile -> {
                    val intent = Intent(baseContext, ProfileActivity::class.java)
                    startActivity(intent)
                    false
                }
                else -> false
            }
        }

        // Obtener el SupportMapFragment y recibir notificaciones cuando el mapa esté listo
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Verificar y solicitar permisos de ubicación
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // Permiso concedido
            enableMyLocation()
        }

        // Añadir un marcador en Bogotá y mover la cámara
        val bogota = LatLng(4.5180, -74.0617)
        mMap.addMarker(MarkerOptions().position(bogota).title("Marcador en Bogotá"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bogota))
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation()
                } else {
                    // Manejar el caso en que el usuario no concede el permiso
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.barra_navegacion, menu)
        return true
    }
}
