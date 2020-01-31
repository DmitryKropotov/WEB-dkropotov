package com.project.models;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class User {

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;


    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "email is " + email + " password is " + password;
    }
}
