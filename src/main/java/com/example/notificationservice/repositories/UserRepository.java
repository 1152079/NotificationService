package com.example.notificationservice.repositories;

import com.example.notificationservice.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

}