package com.example.lab2.services;

import com.example.lab2.entity.User;
import com.example.lab2.Dto.UserDto;
import com.example.lab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<UserDto> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving all users", e);
        }
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    @Transactional
    public void save(UserDto userDto) {
        try {
            User user = modelMapper.map(userDto, User.class);
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while saving user", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while deleting user by id=" + id, e);
        }
    }

    @Override
    public List<UserDto> findByCriteria(String criteria) {
        try {
            List<User> users = userRepository.findUserByCriteria(criteria);
            return users.stream()
                    .map(user -> modelMapper.map(user, UserDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while finding users by criteria", e);
        }
    }
}

