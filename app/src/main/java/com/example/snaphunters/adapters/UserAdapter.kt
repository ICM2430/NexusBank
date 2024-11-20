package com.example.snaphunters.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.snaphunters.ChatActivity
import com.example.snaphunters.R
import com.example.snaphunters.entities.User






class UserAdapter(private val context: Context, private val users: ArrayList<User>) : ArrayAdapter<User>(context, 0, users) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.contacts, parent, false)

        val user = users[position]

        val imageView: ImageView = view.findViewById(R.id.user_image)
        val textView: TextView = view.findViewById(R.id.user_name)
        val button: Button = view.findViewById(R.id.view_location_button)

        if(user.profilePicture != null){
            imageView.setImageBitmap(user.profilePicture)
        }
        else{
            imageView.setImageResource(R.drawable.baseline_person_24)
        }

        textView.text = user.nombre + " " + user.apellido

        if(user.estado == "conectado"){
            val icon: ImageView = view.findViewById(R.id.statusIcon)
            icon.backgroundTintList = context.resources.getColorStateList(R.color.Verde)
        }else if(user.estado == "desconectado"){
            val icon: ImageView = view.findViewById(R.id.statusIcon)
            icon.backgroundTintList = context.resources.getColorStateList(R.color.GrisOscuro)

        }else {
            val icon: ImageView = view.findViewById(R.id.statusIcon)
            icon.backgroundTintList = context.resources.getColorStateList(R.color.Rojo)
        }



        button.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("user_id", user.id)
            context.startActivity(intent)
        }

        return view
    }
}