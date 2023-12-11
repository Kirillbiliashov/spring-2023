package com.example.lab2.services;

import com.example.lab2.entity.User;
import com.example.lab2.repository.OracleUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final OracleUserRepository userRepository;

    public UserServiceImpl(OracleUserRepository userRepository) {
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
}

