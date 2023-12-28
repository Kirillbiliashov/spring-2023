package com.example.lab2.repository;

import com.example.lab2.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.name LIKE %:criteria% OR u.email LIKE %:criteria%")
    List<User> findUserByCriteria(@Param("criteria") String criteria);
}
