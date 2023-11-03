package com.example.lab2.services;

import com.example.lab2.entity.Rating;
import com.example.lab2.entity.Tale;
import com.example.lab2.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RatingService {
    private RatingRepository ratingRepository;

    //Через сетер
    @Autowired
    public void setRatingRepository(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void addRating(Tale tale) {
        ratingRepository.addRating(new Rating(tale));
    }
    public Rating getRatingByTaleId(int taleId) {
        List<Rating> ratings = ratingRepository.getRatings();
        for (Rating rating: ratings) {
            if (taleId == rating.getTale().getId()) {
                return rating;
            }
        }
        return null;
    }
    public void deleteRating(int taleId) {
        Rating ratingToDelete = getRatingByTaleId(taleId);
        if (ratingToDelete != null) {
            ratingRepository.deleteRating(ratingToDelete);
        }
    }
    public void likeTale(Tale tale) {
        ratingRepository.addLike(getRatingByTaleId(tale.getId()));
    }
    public void unLikeTale(Tale tale) {
        ratingRepository.removeLike(getRatingByTaleId(tale.getId()));
    }
    public List<Rating> getBest() {
        List<Rating> ratings = ratingRepository.getRatings();
        List<Rating> sortedRatings = new ArrayList<>(ratings);
        sortedRatings.sort(Comparator.comparingInt(Rating::getLikes).reversed());
        return sortedRatings;
    }
    public void editRating(Tale oldTale, Tale tale) {
        Rating oldRating = getRatingByTaleId(oldTale.getId());
        oldRating.setTale(tale);
    }
}
