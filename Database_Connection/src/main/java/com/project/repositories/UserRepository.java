package com.project.repositories;

import com.project.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean createUser(String email, String password);

    List<User> findAll();

    Optional<User> findByEmail(String email);
}
