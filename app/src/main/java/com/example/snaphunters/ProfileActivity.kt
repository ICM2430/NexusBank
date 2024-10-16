package com.example.snaphunters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.snaphunters.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.result.ActivityResultCallback
import com.example.snaphunters.entities.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private var selectedImageUri: Uri? = null

    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference

    private val getImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            binding.imageView2.setImageURI(selectedImageUri)
            uploadImageToFirebaseStorage()
            Log.d("ProfileActivity", "Imagen seleccionada: $selectedImageUri")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("USERS")

        val getContentGallery = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                uri?.let { loadImage(it) }
            })


        binding.imageView2.setOnClickListener(){
            getImage.launch("image/*")
        }



        val currentUser = auth.currentUser

        if (currentUser != null) {
            updateUI(currentUser)
        } else {
            Toast.makeText(this, "No se ha iniciado sesión", Toast.LENGTH_SHORT).show()
            redirectToLogin()
        }

        binding.btnLogOut.setOnClickListener {
            auth.signOut()
            redirectToLogin()
        }

        binding.imageView2.setOnClickListener {
            getImage.launch("image/*")
        }

        setupBottomNavigation()
    }


    private fun updateUI(user: FirebaseUser) {
        binding.correo.text = user.email
        readOnce()

    }

    private fun redirectToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigationView.selectedItemId = R.id.profile
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.friends -> {
                    startActivity(Intent(this, FriendsActivity::class.java))
                    true
                }
                R.id.menu -> {
                    startActivity(Intent(this, MenuActivity::class.java))
                    true
                }
                R.id.maps -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                    true
                }
                R.id.profile -> true
                else -> false
            }
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedImageUri != null) {
            val storageReference = FirebaseStorage.getInstance().getReference("profilePictures/${auth.currentUser?.uid}")

            storageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    Toast.makeText(this, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
                    Log.d("ProfileActivity", "Imagen subida con éxito")
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        updateUserProfilePicture(uri.toString())
                    }.addOnFailureListener { e ->
                        Log.e("ProfileActivity", "Error al obtener la URL: ${e.message}")
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error al subir la imagen: ${exception.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileActivity", "Error al subir la imagen: ${exception.message}")
                }
        } else {
            Toast.makeText(this, "Selecciona una imagen primero", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserProfilePicture(imageUrl: String) {
        val user = auth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setPhotoUri(Uri.parse(imageUrl))
            .build()

        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                Log.d("ProfileActivity", "Perfil actualizado con la nueva imagen: $imageUrl")
                updateUI(user)
            } else {
                Log.e("ProfileActivity", "Error al actualizar el perfil: ${task.exception?.message}")
            }
        }
    }

    companion object {
        private const val REQUEST_CODE = 101
    }

    fun readOnce() {
        val userRef = database.getReference("users").child(auth.currentUser?.uid.toString())
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val lastname = snapshot.child("lastname").getValue(String::class.java)
                    val username = snapshot.child("username").getValue(String::class.java)

                    binding.nombreUsuario.text = name + " " + lastname
                    binding.username.text = username
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


    private fun loadImage(uri: Uri) {
        val imageStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.imageView2.setImageBitmap(bitmap)
    }

}
