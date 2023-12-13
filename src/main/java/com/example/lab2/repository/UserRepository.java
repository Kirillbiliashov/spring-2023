package com.example.lab2.repository;

import com.example.lab2.entity.User;

import java.util.List;

public interface UserRepository {
    User findById(Long id);
    Iterable<User> findAll();
    User save(User user);
    void deleteById(Long id);

    List<User> findUserByCriteria(String criteria);

}