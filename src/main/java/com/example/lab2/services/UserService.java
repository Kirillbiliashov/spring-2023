package com.example.lab2.services;

import com.example.lab2.entity.User;
import com.example.lab2.repository.InMemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    void save(User user);
    void deleteUser(Long id);
    TreeSet<Long> getFavoriteTales(Long userId);
    TreeSet<Long> getUnreadTales(Long userId);
    void addToFavorites(Long userId, Long taleId);
    void removeFromFavorites(Long userId, Long taleId);
    void addToUnread(Long userId, Long taleId);
    void removeFromUnread(Long userId, Long taleId);
}
