package com.databaseConnection.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
