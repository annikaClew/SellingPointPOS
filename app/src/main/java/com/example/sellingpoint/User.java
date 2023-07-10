package com.example.sellingpoint;

public class User {

    public String email;
    public String firstname;
    public String lastname;
    public String position;
    public String password;

    public User(String email, String firstname, String lastname, String position, String password)
    {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
        this.password = password;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }
}
