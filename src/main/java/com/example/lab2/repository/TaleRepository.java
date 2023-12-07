package com.example.lab2.repository;

import com.example.lab2.entity.Tale;

import java.util.Collection;

public interface TaleRepository {
    Tale findById(Long id);
    Collection<Tale> findAll();
    Tale save(Tale tale);
    void deleteById(Long id);
}
