package com.example.userauthenticationservice.repository;

import com.example.userauthenticationservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User save(User user);

    Optional<User> findByEmail(String email);

}
