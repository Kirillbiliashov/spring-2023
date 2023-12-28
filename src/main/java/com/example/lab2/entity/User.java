package com.example.lab2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String email;

    @ManyToMany(mappedBy = "likedByUsers")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    Set<Tale> favoriteTales = new HashSet<>();

    @ManyToMany(mappedBy = "readByUsers")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    Set<Tale> readTales = new HashSet<>();
}