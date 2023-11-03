package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import com.example.lab2.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final User user = new User();
    public User getUser() {
        return user;
    }
    public List<Tale> getFavoriteTales() {
        return user.getFavoriteTales();
    }
    public List<Tale> getUnreadTales(List<Tale> tales) {
        List<Tale> readTales = user.getReadTales();
        List<Tale> unreadTales = new ArrayList<>();
        for (Tale tale : tales) {
            if (!readTales.contains(tale)) {
                unreadTales.add(tale);
            }
        }
        return unreadTales;
    }
    public boolean isRead(Tale tale) {
        return user.getReadTales().contains(tale);
    }
    public boolean isLiked(Tale tale) {
        return user.getFavoriteTales().contains(tale);
    }
    public void addTaleToRead(Tale tale) {
        user.addTaleToRead(tale);
    }
    public void removeTaleFromRead(Tale tale) {
        user.removeTaleFromRead(tale);
    }
    public void addTaleToFavorite(Tale tale) {
        user.addTaleToFavorite(tale);
    }
    public void removeTaleFromFavorite(Tale tale) {
        user.removeTaleFromFavorite(tale);
    }
    public void editReadTale(Tale oldTale, Tale tale) {
        List<Tale> readTales = user.getReadTales();
        if (readTales.contains(oldTale)) {
            readTales.set(readTales.indexOf(oldTale), tale);
        }
    }
    public void editLikedTale(Tale oldTale, Tale tale) {
        List<Tale> favoriteTales = user.getFavoriteTales();
        if (favoriteTales.contains(oldTale)) {
            favoriteTales.set(favoriteTales.indexOf(oldTale), tale);
        }
    }
}
