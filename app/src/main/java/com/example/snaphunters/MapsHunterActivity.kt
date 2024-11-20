package com.example.snaphunters

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
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
import com.example.snaphunters.databinding.ActivityMapsHunterBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class MapsHunterActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsHunterBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private var currentLocation: LatLng? = null
    private lateinit var database : FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsHunterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        binding.cameraBtn.setOnClickListener {

        }

        binding.playersBtn.setOnClickListener(){
            val intent = Intent(this, HunterPlayersActivity::class.java)
            startActivity(intent)
        }

        binding.leaveBtn.setOnClickListener(){

            cambiarEstado()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
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
                            cargarUbicacionUsuarios()
                            drawMarkerPlayer(userLocation, "Tu ubicación")
                            buscarLugaresCercanos(userLocation, 300, "restaurant")
                            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 18f))

                        }
                    }else{
                        Toast.makeText(this@MapsHunterActivity, "Error loading location", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

    fun drawPlayers(location: LatLng, description: String?) {
        var addressMarker = mMap.addMarker(
            MarkerOptions().position(location).icon(
                bitmapDescriptorFromVector(
                    this,
                    R.drawable.circulo_rojo
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



                if (estado == "cazador" && latitud != null && longitud != null && userId != currentUserId) {
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
}