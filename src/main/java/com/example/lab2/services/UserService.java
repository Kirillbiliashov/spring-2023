package com.example.lab2.services;

import com.example.lab2.Dto.UserDto;

import java.util.*;

public interface UserService {
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    void save(UserDto userDto);
    void deleteUser(Long id);
    List<UserDto> findByCriteria(String criteria);
}
