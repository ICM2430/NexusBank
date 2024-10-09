package com.example.snaphunters

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "users.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_APELLIDO = "apellido"
        private const val COLUMN_USUARIO = "usuario"
        private const val COLUMN_CORREO = "correo"
        private const val COLUMN_CONTRASENA = "contrasena"
        private const val COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_NOMBRE TEXT,"
                + "$COLUMN_APELLIDO TEXT,"
                + "$COLUMN_USUARIO TEXT UNIQUE,"
                + "$COLUMN_CORREO TEXT UNIQUE,"
                + "$COLUMN_CONTRASENA TEXT,"
                + "$COLUMN_FECHA_NACIMIENTO TEXT)")
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Función para el registro: Verificar si el usuario o correo ya existen
    fun checkUserOrEmailExists(usuario: String, correo: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USUARIO = ? OR $COLUMN_CORREO = ?"
        val cursor = db.rawQuery(query, arrayOf(usuario, correo))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Función para el inicio de sesión: Verificar si el usuario y la contraseña son correctos
    fun checkUserCredentials(usuario: String, contrasena: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USUARIO = ? AND $COLUMN_CONTRASENA = ?"
        val cursor = db.rawQuery(query, arrayOf(usuario, contrasena))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Método para agregar un usuario a la base de datos
    fun addUser(nombre: String, apellido: String, usuario: String, correo: String, contrasena: String, fechaNacimiento: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NOMBRE, nombre)
        values.put(COLUMN_APELLIDO, apellido)
        values.put(COLUMN_USUARIO, usuario)
        values.put(COLUMN_CORREO, correo)
        values.put(COLUMN_CONTRASENA, contrasena)
        values.put(COLUMN_FECHA_NACIMIENTO, fechaNacimiento)

        val success = db.insert(TABLE_USERS, null, values)
        db.close()
        return success
    }
}
