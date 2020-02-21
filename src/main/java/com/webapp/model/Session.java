package com.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="session")
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "userId")
    String userEmail;

    public Session(String userEmail) {
        this.userEmail = userEmail;
    }
}
