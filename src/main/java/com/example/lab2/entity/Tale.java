package com.example.lab2.entity;

import lombok.Data;

@Data
public class Tale {
    Long id;
    String title;
    String author;
    int likes = 0;
    public Tale(){}
    public Tale(String title, String author) {
        this.title = title;
        this.author = author;
    }
    public Tale(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public Tale(Long id, String title, String author, int likess) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.likes = likess;
    }
}
