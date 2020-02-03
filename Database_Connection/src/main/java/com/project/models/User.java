package com.project.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "email is " + email + " password is " + password;
    }
}
