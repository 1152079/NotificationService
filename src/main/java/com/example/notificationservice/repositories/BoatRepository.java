package com.example.notificationservice.repositories;

import com.example.notificationservice.models.entities.Boat;
import com.example.notificationservice.models.enums.BoatStatus;
import com.example.notificationservice.models.enums.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoatRepository extends JpaRepository<Boat, String> {

    boolean existsByName(String name);

    Optional<Boat> findByName(String name);

    boolean existsByType(BoatType type);

    Optional<Boat> findById(UUID id);

    List<Boat> findByStatus(BoatStatus status);
}
