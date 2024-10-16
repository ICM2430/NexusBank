package com.example.snaphunters

import android.Manifest
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.snaphunters.databinding.ActivityMapsBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.views.overlay.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var geocoder: Geocoder
    private lateinit var roadManager: RoadManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    lateinit var locationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    private var currentLocation: android.location.Location? = null


    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference

    private lateinit var vel: ValueEventListener

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

        binding.btnCompass.setOnClickListener {
            val intent = Intent(baseContext, CompassActivity::class.java)
            startActivity(intent)
        }

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

        geocoder = Geocoder(baseContext)
        roadManager = OSRMRoadManager(this, "ANDROID")
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = createLocationRequest()
        locationCallback = createLocationCallBack()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.setAllGesturesEnabled(true)
        mMap.uiSettings.isZoomControlsEnabled = true

        if (currentLocation != null) {
            val currentLatLng = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            drawMarker(currentLatLng, "Current Location")
        } else {
            val bogota = LatLng(4.62, -74.07)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bogota, 20f))
        }

        mMap.setOnMapLongClickListener { latLng ->
            val address = findAddress(latLng)
            mMap.clear()
            drawRoute(LatLng(currentLocation!!.latitude, currentLocation!!.longitude), latLng)
        }


    }

    override fun onResume() {


        super.onResume()
        startLocationUpdates()
    }

    override fun onStart() {
        super.onStart()


    }

    override fun onPause() {
        stopLocationUpdates()
        super.onPause()

    }

    fun findAddress(location: LatLng): String? {
        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 2)
        if (addresses != null && !addresses.isEmpty()) {
            val addr = addresses.get(0)
            val locname = addr.getAddressLine(0)
            return locname
        }
        return null
    }

    private var roadOverlay: Polyline? = null

    fun drawRoute(start: LatLng, finish: LatLng) {
        val routePoints = ArrayList<GeoPoint>()

        var start2 = GeoPoint(start.latitude, start.longitude)
        var finish2 = GeoPoint(finish.latitude, finish.longitude)

        routePoints.add(start2)
        routePoints.add(finish2)

        val road = roadManager.getRoad(routePoints)
        Toast.makeText(this, "Distancia: ${road.mLength} km", Toast.LENGTH_LONG).show()

        if (mMap != null) {
            roadOverlay?.let {
                mMap.clear()
            }

            roadOverlay = RoadManager.buildRoadOverlay(road)
            roadOverlay!!.getOutlinePaint().setColor(Color.RED)
            roadOverlay!!.getOutlinePaint().setStrokeWidth(10F)

            var nombre = findAddress(finish)


            drawMarkerPlayer(start, "Posición actual")
            drawMarker(finish, nombre)
            val latLngPoints = roadOverlay!!.points.map { LatLng(it.latitude, it.longitude) }
            mMap.addPolyline(PolylineOptions().addAll(latLngPoints).color(Color.YELLOW).width(10F))
        }
    }

    fun findLocation(address: String): LatLng? {
        val addresses = geocoder.getFromLocationName(address, 2)
        if (addresses != null && !addresses.isEmpty()) {
            val addr = addresses.get(0)
            val location = LatLng(
                addr.latitude, addr.longitude
            )
            return location
        }
        return null
    }


    fun drawMarker(location: LatLng, description: String?) {
        var addressMarker = mMap.addMarker(
            MarkerOptions().position(location).icon(
                bitmapDescriptorFromVector(
                    this,
                    R.drawable.baseline_location_on_24
                )
            )
        )!!

        if (description != null) {
            addressMarker.title = description
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20f))
        //drawRoute(bogota, GeoPoint(location.latitude, location.longitude))
    }

    fun drawMarkerPlayer(location: LatLng, description: String?) {

        deletePlayerMarker(location)


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

        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        //drawRoute(bogota, GeoPoint(location.latitude, location.longitude))
    }

    fun deletePlayerMarker(latLng: LatLng) {
        val marker = mMap.addMarker(MarkerOptions().position(latLng).title("Marcador"))
        marker?.remove()
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


    /* */


    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    fun onLocationChanged(location: android.location.Location) {
        val currentLat = currentLocation?.latitude
        val currentLon = currentLocation?.longitude

        if (currentLocation == null || distance(
                currentLat ?: 0.0, currentLon ?: 0.0,
                location.latitude, location.longitude
            ) > 0.01
        ) {

            currentLocation = location
            updateMapWithLocation(location)

        }
    }


    fun distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
        val latDistance = Math.toRadians(lat1 - lat2)
        val lngDistance = Math.toRadians(long1 - long2)
        val a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        val result = 6371 * c
        return Math.round(result * 100.0) / 100.0;
    }


    fun createLocationCallBack(): LocationCallback {
        val locationCallback = object : LocationCallback() {
            @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                if (result != null) {
                    val location = result.lastLocation!!
                    onLocationChanged(location)

                    drawMarkerPlayer(LatLng(location.latitude, location.longitude), "Posición actual")

                }
            }
        }
        return locationCallback

    }

    private fun writeLocations(location: LatLng){
//        val LOCATIONS = "Locations/"
//        val locationKey = "${location.latitude}_${location.longitude}".replace(".", "_")
//        myRef = database.getReference(LOCATIONS + locationKey)
//        myRef.setValue(location)
//        val key = myRef.push().key
//        myRef = database.getReference(LOCATIONS + key)
//        myRef.setValue(location)


    }

    private fun updateMapWithLocation(location: android.location.Location) {
        writeLocations(LatLng(location.latitude, location.longitude))
    }

    fun stopLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback)
    }


    fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 30000
        )
            .setWaitForAccurateLocation(true)
            .setMinUpdateIntervalMillis(5000)
            .build()
        return locationRequest
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.barra_navegacion, menu)
        return true
    }
/*
    fun readOnce(){
        myRef = database.getReference(users)
        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.listUsers.removeAllViews()
                for(child in snapshot.children){
                    val user = child.getValue<User>()
                    val tv = TextView(baseContext)
                    tv.text = user?.toString()
                    binding.listUsers.addView(tv)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

*/
private fun subscribeToUserChanges() {


}



}
