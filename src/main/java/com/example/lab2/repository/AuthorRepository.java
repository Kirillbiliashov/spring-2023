package com.example.lab2.repository;

import com.example.lab2.entity.Author;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ListCrudRepository<Author, Long> {
    Author findByName(String author);
}
