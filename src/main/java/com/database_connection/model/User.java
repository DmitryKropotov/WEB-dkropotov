package com.database_connection.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
