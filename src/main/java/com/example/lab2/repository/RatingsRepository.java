package com.example.lab2.repository;

import com.example.lab2.entity.Rating;
import com.example.lab2.entity.Tale;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingsRepository {

    private List<Rating> ratings = List.of(
            new Rating(1, 756, 194),
            new Rating(2, 214, 56),
            new Rating(3, 435, 125),
            new Rating(4, 843, 219),
            new Rating(5, 123, 34),
            new Rating(6, 698, 178),
            new Rating(7, 299, 89),
            new Rating(8, 512, 201),
            new Rating(9, 0, 0),
            new Rating(10, 911, 72)
    );

    public List<Rating> getRatings() {
        return ratings;
    }

    public Rating getRating(int taleId) {
        return ratings.get(taleId - 1);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void editRating(Rating rating) {
        int idx = rating.getTaleId() - 1;
        ratings.remove(idx);
        ratings.add(idx, rating);
    }

    public void deleteRating(int taleId) {
        ratings.remove(taleId - 1);
    }



}
