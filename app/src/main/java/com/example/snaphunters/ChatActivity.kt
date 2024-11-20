package com.example.snaphunters

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snaphunters.adapters.MessageAdapter
import com.example.snaphunters.databinding.ActivityChatBinding
import com.example.snaphunters.entities.Message
import com.example.snaphunters.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding

    private lateinit var database: FirebaseDatabase

    private lateinit var auth: FirebaseAuth

    private lateinit var messageAdapter: MessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()

        binding.recyclerViewMessages.layoutManager = LinearLayoutManager(this)


        val userId = intent.getStringExtra("user_id")
        if (userId != null) {
            loadUserInfo(userId)
        }

        binding.btnSendMessage.setOnClickListener() {
            enviarMensaje()
        }

        binding.btnBack.setOnClickListener() {
            val intent = Intent(baseContext, FriendsActivity::class.java)
            startActivity(intent)
        }

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null && currentUserId != null) {
            leerMensaje(userId, currentUserId)
        }



    }


    private fun loadUserInfo(userId: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (singleSnapshot in dataSnapshot.children) {
                    if (singleSnapshot.key == userId) {
                        val myUser = singleSnapshot.getValue(User::class.java)

                        if (myUser != null) {
                            binding.nombreTxt.text = myUser.nombre + " " + myUser.apellido
                            binding.usernameTxt.text = myUser.usuario

                            when (myUser.estado) {
                                "conectado" -> {
                                    binding.statusTxt.text = "En linea"
                                    binding.statusTxt.setTextColor(
                                        ContextCompat.getColor(
                                            baseContext,
                                            R.color.Verde
                                        )
                                    )
                                    binding.statusIcon.backgroundTintList =
                                        ContextCompat.getColorStateList(baseContext, R.color.Verde)
                                }

                                "desconectado" -> {
                                    binding.statusTxt.text = "Desconectado"
                                    binding.statusTxt.setTextColor(
                                        ContextCompat.getColor(
                                            baseContext,
                                            R.color.white
                                        )
                                    )
                                    binding.statusIcon.backgroundTintList =
                                        ContextCompat.getColorStateList(baseContext, R.color.gris)
                                }

                                else -> {
                                    binding.statusTxt.text = "En juego"
                                    binding.statusTxt.setTextColor(
                                        ContextCompat.getColor(
                                            baseContext,
                                            R.color.Rojo
                                        )
                                    )
                                    binding.statusIcon.backgroundTintList =
                                        ContextCompat.getColorStateList(baseContext, R.color.Rojo)
                                }
                            }

                            descargarFotoUsuario(userId) { bitmap ->
                                if (bitmap != null) {
                                    binding.PhotoProfileImg.setImageBitmap(bitmap)
                                } else {
                                    binding.PhotoProfileImg.setImageResource(R.drawable.baseline_person_24)
                                }
                            }
                        }
                    }
                }


            }



            override fun onCancelled(error: DatabaseError) {
                Log.w("ChatActivity", "Failed to read value.", error.toException())
            }
        })
    }

    private fun descargarFotoUsuario(userId: String, callback: (Bitmap?) -> Unit) {
        val storageReference = FirebaseStorage.getInstance().getReference("profileImages/$userId")
        val ONE_MEGABYTE: Long = 1024 * 1024

        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            callback(bitmap)
        }.addOnFailureListener { exception ->
            Log.e("ChatActivity", "Failed to download image for user $userId", exception)
            callback(null)
        }

    }

    private fun enviarMensaje() {
        val userId = intent.getStringExtra("user_id")
        val mensaje = binding.mensajeTxt.text.toString()
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        if (mensaje.isNotEmpty()) {

        if (userId != null && currentUserId != null) {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("mensajes")

            val mensajeData = mapOf(
                "enviadoPor" to currentUserId,
                "mensaje" to mensaje,
                "recibidoPor" to userId
            )

            myRef.push().setValue(mensajeData).addOnCompleteListener {
                if (it.isSuccessful) {
                    binding.mensajeTxt.text.clear()
                    leerMensaje(userId, currentUserId)
                }
            }
        }

    }
    }

    private fun leerMensaje(userId: String, currentUserId: String) {
        val chat: ArrayList<Message> = ArrayList()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("mensajes")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chat.clear()
                for (singleSnapshot in dataSnapshot.children) {
                    val mensaje = singleSnapshot.getValue(Message::class.java)
                    if (mensaje != null) {
                        if ((mensaje.enviadoPor == userId && mensaje.recibidoPor == currentUserId) ||
                            (mensaje.enviadoPor == currentUserId && mensaje.recibidoPor == userId)) {
                            chat.add(mensaje)
                        }
                    }

                    messageAdapter = MessageAdapter(baseContext, chat)
                    binding.recyclerViewMessages.adapter = messageAdapter
                    messageAdapter.notifyDataSetChanged()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ChatActivity", "Failed to read value.", error.toException())
            }
        })
    }
}