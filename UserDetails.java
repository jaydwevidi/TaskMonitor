package com.example.myapplication;

public class UserDetails {
    private String name="No name Provided";
    private String email="No e-Mail Provided";

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public UserDetails(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
