package com.example.lab2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "TALES")
@NamedQuery(
        name = "Tale.findFavoriteTalesByUserId",
        query = "SELECT DISTINCT t FROM Tale t JOIN t.likedByUsers u WHERE u.id = ?1"
)public class Tale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "id")
    @ToString.Exclude @EqualsAndHashCode.Exclude
    Author author;

    @ManyToMany
    @JoinTable(
            name = "TALES_LIKES",
            joinColumns = @JoinColumn(name = "tale_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Set<User> likedByUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "TALES_READS",
            joinColumns = @JoinColumn(name = "tale_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Set<User> readByUsers = new HashSet<>();
}