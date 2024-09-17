package com.webapp.model;


import javax.persistence.*;

@Entity
@Table(name="session")
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

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    public Session() {
    }

    public Session(int id, String userEmail) {
        this.id = id;
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
