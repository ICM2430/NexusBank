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

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private var selectedImageUri: Uri? = null

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

        checkPermission()

        auth = FirebaseAuth.getInstance()
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

        binding.btnChangeProfilePicture.setOnClickListener {
            getImage.launch("image/*")
        }

        setupBottomNavigation()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
        }
    }

    private fun updateUI(user: FirebaseUser) {
        binding.correo.text = user.email ?: "Correo no disponible"
        binding.textView6.text = user.displayName ?: "Nombre no disponible" // Nombre completo
        binding.textView7.text = user.displayName ?: "Nombre de usuario no disponible" // Nombre de usuario

        Glide.with(this)
            .load(user.photoUrl)
            .placeholder(R.drawable.default_profile_icon)
            .circleCrop()
            .into(binding.imageView2)

        binding.edad.text = "Edad: Información no disponible"
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
}
