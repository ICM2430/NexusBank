package com.example.snaphunters

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.snaphunters.databinding.ActivityMapsBountyHuntingBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class MapsBountyHuntingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBountyHuntingBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private var currentLocation: LatLng? = null
    private lateinit var database : FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBountyHuntingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {


                for (location in locationResult.locations) {
                    if (location != null) {
                        val userLocation = LatLng(location.latitude, location.longitude)
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            val userRef = database.getReference("usuarios").child(userId)
                            userRef.child("latitud").setValue(location.latitude)
                            userRef.child("longitud").setValue(location.longitude)
                            currentLocation = userLocation
                            mMap?.clear()

                            drawMarkerPlayer(userLocation, "Tu ubicación")
                            buscarLugaresCercanos(userLocation, 300, "restaurant")
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))
                            cargarUbicacionUsuarios()


                        }
                    }else{
                        Toast.makeText(this@MapsBountyHuntingActivity, "Error loading location", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding.playersBtn.setOnClickListener(){
            val intent = Intent(this, BHPlayersActivity::class.java)
            startActivity(intent)
        }

        binding.leaveBtn.setOnClickListener(){
                cambiarEstado()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onStart() {
        super.onStart()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

        // Verifica si mMap está inicializado
        if (::mMap.isInitialized) {
            mMap.clear()
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else {
            Toast.makeText(this, "Google Map is not ready", Toast.LENGTH_SHORT).show()
        }



    }




    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.uiSettings.isZoomControlsEnabled = true
    }

    fun buscarLugaresCercanos(location: LatLng, radius: Int, type: String) {
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=${location.latitude},${location.longitude}" +
                "&radius=$radius" +
                "&type=$type" +
                "&key=${getString(R.string.google_maps_key)}"

        val requestQueue = Volley.newRequestQueue(this)

        val request = StringRequest(com.android.volley.Request.Method.GET, url, { response ->
            val jsonObject = JSONObject(response)
            val results = jsonObject.getJSONArray("results")
            for (i in 0 until results.length()) {
                val place = results.getJSONObject(i)
                val name = place.getString("name")
                val lat = place.getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lat")
                val lng = place.getJSONObject("geometry")
                    .getJSONObject("location").getDouble("lng")

                // Añade un marcador en el mapa
                val position = LatLng(lat, lng)
                drawRestaurants(position, name)
            }
        }, { error ->
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        })

        requestQueue.add(request)
    }



    override fun onStop() {
        super.onStop()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun drawMarkerPlayer(location: LatLng, description: String?) {

        var addressMarker = mMap.addMarker(
            MarkerOptions().position(location).icon(
                bitmapDescriptorFromVector(
                    this,
                    R.drawable.baseline_circle_24
                )
            )
        )!!

        if (description != null) {
            addressMarker.title = description
        }

    }

    fun drawRestaurants(location: LatLng, description: String?) {

        var addressMarker = mMap.addMarker(
            MarkerOptions().position(location).icon(
                bitmapDescriptorFromVector(
                    this,
                    R.drawable.baseline_restaurant_24
                )
            )
        )!!

        if (description != null) {
            addressMarker.title = description
        }

    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
        vectorDrawable.setBounds(
            0,
            0,
            vectorDrawable.getIntrinsicWidth(),
            vectorDrawable.getIntrinsicHeight()
        );
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(),
            Bitmap.Config.ARGB_8888
        );
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }






    fun drawPlayers(location: LatLng, description: String?) {
        if (::mMap.isInitialized) {
            val circleOptions = CircleOptions()
                .center(location)
                .radius(100.0) // Radius in meters
                .strokeColor(ContextCompat.getColor(this, R.color.Azul))
                .fillColor(Color.argb(128, 127, 0, 0))
                .strokeWidth(2f)
                .clickable(true)

            val circle = mMap.addCircle(circleOptions)

            if (description != null) {
                circle.tag = description
            }

            mMap.setOnCircleClickListener { clickedCircle ->
                if (clickedCircle == circle) {
                    Toast.makeText(this, clickedCircle.tag as String, Toast.LENGTH_SHORT).show()
                }
            }

            Log.d("MapsActivity", "Circle drawn at: ${location.latitude}, ${location.longitude}")
        } else {
            Log.e("MapsActivity", "Google Map is not initialized")
        }
    }


    private fun cargarUbicacionUsuarios() {
        val db = FirebaseDatabase.getInstance()
        val usersRef = db.reference.child("usuarios")
        val currentUserId = auth.currentUser?.uid

        usersRef.get().addOnSuccessListener { dataSnapshot ->
            for (userSnapshot in dataSnapshot.children) {
                val userMap = userSnapshot.getValue(User::class.java)
                val usuario = userMap?.usuario
                val estado = userMap?.estado ?: "desconectado"
                val latitud = userMap?.latitud
                val longitud = userMap?.longitud
                val userId = userSnapshot.key



                if (estado == "cazarecompensas" && latitud != null && longitud != null && userId != currentUserId) {
                    val userLocation = LatLng(latitud, longitud)
                    drawPlayers(userLocation, "Usuario ${usuario}")

                }
            }
        }.addOnFailureListener { exception ->
            Log.e("MapsActivity", "Error al cargar usuarios: ${exception.message}")
        }
    }

    fun cambiarEstado(){
        val user = auth.currentUser
        val db = FirebaseDatabase.getInstance()

        if (user != null) {
            val userRef = db.reference.child("usuarios").child(user.uid)
            userRef.child("estado").setValue("conectado")
                .addOnSuccessListener {
                    Toast.makeText(
                        baseContext,
                        "Estado actualizado a cazarecompensas.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        baseContext,
                        "Error al actualizar el estado: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(baseContext, "No se encontró un usuario autenticado.", Toast.LENGTH_SHORT).show()
        }
    }
}