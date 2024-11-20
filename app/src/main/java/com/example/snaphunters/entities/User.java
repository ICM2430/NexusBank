package com.example.snaphunters.entities;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class User {
    private String id;
    private String usuario;
    private String nombre;
    private String apellido;
    private Bitmap profilePicture;
    private double latitud;
    private double longitud;
    private String estado;

    public User(String id, String usuario, String nombre, String apellido, Bitmap profilePicture, double latitud, double longitud, String estado) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.profilePicture = profilePicture;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
