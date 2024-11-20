package com.example.snaphunters

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.adapters.UserAdapter
import com.example.snaphunters.databinding.ActivityBhplayersBinding
import com.example.snaphunters.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class BHPlayersActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBhplayersBinding
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBhplayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter(this, ArrayList())
        binding.listContacts.adapter = adapter

        loadUsers()

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MapsBountyHuntingActivity::class.java)
            startActivity(intent)
        }

    }

    private fun loadUsers() {
        val db = FirebaseDatabase.getInstance()
        val usersRef = db.reference.child("usuarios")
        val storage = FirebaseStorage.getInstance()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        usersRef.get().addOnSuccessListener { dataSnapshot ->
            val users = ArrayList<User>()
            for (userSnapshot in dataSnapshot.children) {
                val userId = userSnapshot.key.toString()
                if (userId != currentUserId) {
                    val userMap = userSnapshot.value as? Map<*, *>
                    val estado = userMap?.get("estado") as? String ?: "no disponible"
                    if (estado == "cazarecompensas") {
                        val user = User()
                        user.id = userId
                        user.estado = estado
                        user.nombre = userMap?.get("nombre")?.toString() ?: "Desconocido"
                        user.apellido = userMap?.get("apellido")?.toString() ?: "Desconocido"

                        Log.i("BHPlayersActivity", "User: $user")
                        val imageRef = storage.getReference("profileImages/${user.id}")
                        val ONE_MEGABYTE: Long = 1024 * 1024
                        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            user.profilePicture = bitmap
                            users.add(user)
                            adapter.clear()
                            adapter.addAll(users)
                        }
                    }
                }
            }
        }
    }

}