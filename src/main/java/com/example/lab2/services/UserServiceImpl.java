package com.example.lab2.services;


import com.example.lab2.entity.User;
import com.example.lab2.repository.InMemoryUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeSet;

@Service
public class UserServiceImpl implements UserService {
    private final InMemoryUserRepository userRepository;

    public UserServiceImpl(InMemoryUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public TreeSet<Long> getFavoriteTales(Long userId) {
        User user = userRepository.findById(userId);
        return user != null ? user.getFavoriteTales() : null;
    }
    @Override
    public TreeSet<Long> getUnreadTales(Long userId) {
        User user = userRepository.findById(userId);
        return user != null ? user.getUnreadTales() : null;
    }
    @Override
    public void addToFavorites(Long userId, Long taleId) {
        User user = userRepository.findById(userId);
        user.getFavoriteTales().add(taleId);
        userRepository.save(user);
    }
    @Override
    public void removeFromFavorites(Long userId, Long taleId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            user.getFavoriteTales().remove(taleId);
            userRepository.save(user);
        }
    }
    @Override
    public void addToUnread(Long userId, Long taleId) {
        User user = userRepository.findById(userId);
        user.getUnreadTales().add(taleId);
        userRepository.save(user);
    }
    @Override
    public void removeFromUnread(Long userId, Long taleId) {
        User user = userRepository.findById(userId);

        if (user != null) {
            user.getUnreadTales().remove(taleId);
            userRepository.save(user);
        }
    }
}

