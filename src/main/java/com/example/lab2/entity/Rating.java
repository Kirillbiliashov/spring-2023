package com.example.lab2.entity;

public class Rating {
    private int taleId;
    private int likes;
    private int dislikes;

    public Rating(int taleId, int likes, int dislikes) {
        this.taleId = taleId;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public Rating() {}

    public int getTaleId() {
        return taleId;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public double getLikeRatio() {
        return  (double) likes / (double) dislikes;
    }

}
