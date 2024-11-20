package com.example.snaphunters

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.SensorManager
import android.location.Geocoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.snaphunters.databinding.ActivityMapsBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import org.json.JSONObject
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapsBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private var currentLocation: LatLng? = null
    private lateinit var database : FirebaseDatabase
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.google_maps_key))
        }

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        binding.btnCompass.setOnClickListener {
            val intent = Intent(baseContext, CompassActivity::class.java)
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
                        Toast.makeText(this@MapsActivity, "Error loading location", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }



        val currentUserId = auth.currentUser?.uid


// Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                Log.i(TAG, "Place: ${place.name}, ${place.id}, ${place.latLng}")
                place.latLng?.let {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 15f))
                    drawMarkerPlayer(it, place.name)
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        binding.bottomNavigationView.selectedItemId = R.id.maps
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }


    fun drawPlayers(location: LatLng, description: String?) {
        var addressMarker = mMap.addMarker(
            MarkerOptions().position(location).icon(
                bitmapDescriptorFromVector(
                    this,
                    R.drawable.circulo_azul
                )
            )
        )!!

        if (description != null) {
            addressMarker.title = description
        }

    }
    fun drawPlayersInGame(location: LatLng, description: String?) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.barra_navegacion, menu)
        return true
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



                if (estado == "conectado" && latitud != null && longitud != null && userId != currentUserId) {
                    val userLocation = LatLng(latitud, longitud)
                    drawPlayers(userLocation, "Usuario ${usuario}")
                } else if (estado == "cazarecompensas" && latitud != null && longitud != null && userId != currentUserId) {
                    val userLocation = LatLng(latitud, longitud)
                    drawPlayersInGame(userLocation, "Usuario ${usuario}")
                }else if (estado == "cazador" && latitud != null && longitud != null && userId != currentUserId) {
                    val userLocation = LatLng(latitud, longitud)
                    drawPlayersInGame(userLocation, "Usuario ${usuario}")
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("MapsActivity", "Error al cargar usuarios: ${exception.message}")
        }
    }


}
