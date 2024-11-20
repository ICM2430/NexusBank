package com.example.snaphunters

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.snaphunters.databinding.ActivityHunterInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class HunterInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHunterInfoBinding

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHunterInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener() {
            cambiarEstado()
            intent = Intent(this, MapsHunterActivity::class.java)
            startActivity(intent)
        }

    }


    fun cambiarEstado(){
        val user = auth.currentUser
        val db = FirebaseDatabase.getInstance()

        if (user != null) {
            val userRef = db.reference.child("usuarios").child(user.uid)
            userRef.child("estado").setValue("cazador")
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