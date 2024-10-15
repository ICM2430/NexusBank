package com.example.snaphunters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class ContactsAdapter(context: Context?, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.
        contacts, parent, false)
    }
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val tvid = view!!.findViewById<TextView>(R.id.idContacto)
        val tvname = view!!.findViewById<TextView>(R.id.nombre)
        val id = cursor!!.getInt(0)
        val name = cursor!!.getString(1)
        tvid.text=id.toString()
        tvname.text = name
    }
}