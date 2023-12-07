package com.example.lab2.entity;

import lombok.Value;

import java.util.TreeSet;

@Value
    public class User {
        Long id;
        String name;
        String email;
        TreeSet<Long> favoriteTales = new TreeSet<>();
        TreeSet<Long> unreadTales = new TreeSet<>();
    }
