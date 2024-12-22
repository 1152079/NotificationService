package com.example.notificationservice.models.entities;

import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    protected UUID id;
    public UUID getId() {
        return id;
    }
    protected void setId(UUID id) {
        this.id = id;
    }
}
