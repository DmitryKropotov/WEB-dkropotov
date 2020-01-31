package com.project.repositories;

import com.project.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    boolean createUser(String email, String password);

    List<User> selectAllUsers();

    Optional<User> selectUserByEmail(String email);
}
