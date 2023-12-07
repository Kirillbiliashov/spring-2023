package com.example.lab2.entity;

import lombok.Value;

import java.util.Set;
import java.util.TreeSet;

@Value
public class Tale {
    Long id;
    String title;
    String author;
    Set<Long> likes = new TreeSet<>();
}
