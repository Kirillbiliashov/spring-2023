package com.example.lab2.entity;

import lombok.Data;
import lombok.Value;

@Data
public class User {
    Long id;
    String name;
    String email;

    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(){}
}

