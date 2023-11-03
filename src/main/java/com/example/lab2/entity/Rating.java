package com.example.lab2.entity;

import lombok.Data;

@Data
public class Rating {
    private Tale tale;
    private int likes = 0;
    public Rating(Tale tale, int likes) {
        this.tale = tale;
        this.likes = likes;
    }
    public Rating(Tale tale) {
        this.tale = tale;
    }
    public void addLike(){
        likes++;
    }
    public void removeLike(){
        likes--;
    }

}
