package com.example.myapplication;

public class UserDetails {
    private String name;
    private String email;

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
