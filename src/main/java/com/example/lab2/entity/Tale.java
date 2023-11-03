package com.example.lab2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tale {
    private int id;
    private String title;
    private String author;
    private String content;
    private boolean isRead = false;
    public Tale(int id, String title, String author, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
    }
}
