package com.example.lab2.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class User {
    private int id;
    private List<Tale> favoriteTales = new ArrayList<>(List.of(
            new Tale(1,"The Adventure of the Lost Key","",""),
            new Tale(2,"The Mystery of the Hidden Treasure","",""),
            new Tale(3,"The Enchanted Forest","","")
    ));
    private List<Tale> readTales = new ArrayList<>(List.of(
            new Tale(8,"The Princess and the Frog","",""),
            new Tale(9,"The Wizard's Spell","",""),
            new Tale(10, "The Haunted Mansion","","")
    ));
    public boolean isRead(int taleId) {
        if (readTales == null) return false;
        for (Tale tale : getReadTales()) {
            if (tale.getId() == taleId) {
                return true;
            }
        }
        return false;
    }
    public boolean isLiked(int taleId) {
        if (favoriteTales == null) return false;
        for (Tale tale : getFavoriteTales()) {
            if (tale.getId() == taleId) {
                return true;
            }
        }
        return false;
    }
    public void addTaleToFavorite(Tale tale){
        if (!favoriteTales.contains(tale)) {
            favoriteTales.add(tale);
        }
    }
    public void removeTaleFromFavorite(Tale tale){
        favoriteTales.remove(tale);
    }
    public void addTaleToRead(Tale tale){
        if (!readTales.contains(tale)) {
            readTales.add(tale);
        }
    }
    public void removeTaleFromRead(Tale tale){
        readTales.remove(tale);
    }
}
