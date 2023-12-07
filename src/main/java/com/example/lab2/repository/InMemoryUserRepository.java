package com.example.lab2.repository;

import com.example.lab2.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

@Repository
public class InMemoryUserRepository implements UserRepository {
    TreeMap<Long, User> users = new TreeMap<>();

    @Override
    public User findById(Long id) {
        return users.get(id); // returns User or null
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User save(User user) {
// HTTP PUT:
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }

    public InMemoryUserRepository() {
        // Test Data
        User user1 = new User(1L, "Andrukhiv Vita", "vitandrukhiv@gmail.com");
        User user2 = new User(2L, "Yaskulska Hrystyna", "yaskulskahrystyna@gmail.com");
        User user3 = new User(3L, "Biliashow Kyrylo", "biliashov@gmail.com");

        // Додаємо всі казки до списку непрочитаних казок для кожного користувача
        TreeSet<Long> allTales = new TreeSet<>(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L));
        user1.getUnreadTales().addAll(allTales);
        user2.getUnreadTales().addAll(allTales);
        user3.getUnreadTales().addAll(allTales);

        users.put(1L, user1);
        users.put(2L, user2);
        users.put(3L, user3);
    }
}
