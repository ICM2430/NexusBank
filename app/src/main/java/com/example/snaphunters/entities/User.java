package com.example.snaphunters.entities;

import com.google.android.gms.maps.model.LatLng;

public class User {
    private String email;
    private String username;
    private String name;
    private String lastName;
    private String profilePicture;
    private LatLng location;

    public User(String email, String name, String lastName, String profilePicture, LatLng location, String username) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.location = location;
    }


    public User(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
