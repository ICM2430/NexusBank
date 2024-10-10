package com.example.snaphunters

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityFriendsBinding

class FriendsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFriendsBinding
    private lateinit var adapter : ContactsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ContactsAdapter(this, null, 0)
        binding.listContacts.adapter = adapter



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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            updateUI()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }


    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            updateUI()
        } else {
            // Handle permission denial
        }
    }


    private fun updateUI() {
        val projection = arrayOf(ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
        val cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, projection, null, null, ContactsContract.Contacts._ID + " ASC")
        adapter.changeCursor(cursor)
    }
}