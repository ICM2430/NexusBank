package com.example.snaphunters

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.snaphunters.databinding.ActivityRegisterBinding
import com.example.snaphunters.entities.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth

    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val getContentGallery = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                uri?.let { loadImage(it) }
            })


        binding.imageView5.setOnClickListener {
            getContentGallery.launch("image/*")
        }


        binding.btnRegister.setOnClickListener(){
            if(validateForm(binding.txtEmail.text.toString(), binding.txtPassword.text.toString())){
                registrarUsuario(
                    binding.txtName.text.toString(),
                    binding.txtLastName.text.toString(),
                    binding.txtUsername.text.toString(),
                    binding.txtEmail.text.toString(),
                    binding.txtPassword.text.toString()
                )
            }else{
                Toast.makeText(this, "Error en el formulario", Toast.LENGTH_SHORT).show()
            }
        }



        }


    @SuppressLint("MissingPermission")
    private fun registrarUsuario(nombre: String, apellido: String, usuario : String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val db = FirebaseDatabase.getInstance()
                saveImageToFirebase(binding.imageView5.drawable.toBitmap())

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
                    return@addOnCompleteListener
                }

                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    val userData = User()
                    userData.nombre = nombre
                    userData.apellido = apellido
                    userData.usuario = usuario
                    userData.latitud = location!!.latitude
                    userData.longitud = location!!.longitude
                    userData.estado = "conectado"
                    db.reference.child("usuarios").child(user!!.uid).setValue(userData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                baseContext,
                                "Usuario registrado exitosamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(baseContext, WelcomeActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                baseContext,
                                "Error al registrar el usuario en la base de datos.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        baseContext,
                        "Error al obtener la ubicación: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(baseContext, "Error al registrar el usuario.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(regex.toRegex())
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = false
        if (email.isEmpty()) {
            binding.txtEmail.setError("Required!")
        } else if (!validEmailAddress(email)) {
            binding.txtEmail.setError("Invalid email address")
        } else if (password.isEmpty()) {
            binding.txtPassword.setError("Required!")
        } else if (password.length < 6) {
            binding.txtPassword.setError("Password should be at least 6 characters long!")
        } else if (binding.txtName.text.isEmpty()) {
            binding.txtName.setError("Required!")
        }else if (binding.txtLastName.text.isEmpty()) {
            binding.txtLastName.setError("Required!")
        }else {
            valid = true
        }
        return valid
    }

    private fun loadImage(uri: Uri) {
        val imageStream = contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.imageView5.setImageBitmap(bitmap)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val i = Intent(this, WelcomeActivity::class.java)
            startActivity(i)
        }
    }


    private fun saveImageToFirebase(imageBitmap: Bitmap) {
        val user = Firebase.auth.currentUser
        if (user != null) {
            val profileImageRef = storageRef.child("profileImages/${user.uid}")

            val baos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = profileImageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // Toast.makeText(this, "Imagen subida con éxito", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }


}
