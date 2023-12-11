package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import com.example.lab2.entity.User;

import java.util.*;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void save(User user);
    void deleteUser(Long id);
}
