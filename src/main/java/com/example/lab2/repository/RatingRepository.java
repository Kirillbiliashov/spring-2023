package com.example.lab2.repository;

import com.example.lab2.entity.Rating;
import com.example.lab2.entity.Tale;

import java.util.ArrayList;
import java.util.List;

public class RatingRepository {
    private List<Rating> ratings = new ArrayList<>(List.of(
            new Rating(new Tale(1,"The Adventure of the Lost Key","",""),10),
            new Rating(new Tale(2,"The Mystery of the Hidden Treasure","",""), 56),
            new Rating(new Tale(3,"The Enchanted Forest","",""), 125),
            new Rating(new Tale(4,"The Quest for the Magic Sword","",""), 219),
            new Rating(new Tale(5,"The Secret of the Old Castle","",""), 194),
            new Rating(new Tale(6,"The Dragon's Lair","",""), 45),
            new Rating(new Tale(7,"The Magical Journey","",""), 5),
            new Rating(new Tale(8,"The Princess and the Frog","",""), 8),
            new Rating(new Tale(9,"The Wizard's Spell","",""), 3),
            new Rating(new Tale(10, "The Haunted Mansion","",""), 85)
    ));
    public List<Rating> getRatings() {
        return ratings;
    }
    public void addRating(Rating rating) {
        ratings.add(rating);
    }
    public void deleteRating(Rating rating) {
        ratings.remove(rating);
    }
    public void addLike(Rating rating) {
        rating.addLike();
    }
    public void removeLike(Rating rating) {
        rating.removeLike();
    }
}
