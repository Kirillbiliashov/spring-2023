package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaleRepository {
    private List<Tale> tales = new ArrayList<>(List.of(
            new Tale(1,"The Adventure of the Lost Key","",""),
            new Tale(2,"The Mystery of the Hidden Treasure","",""),
            new Tale(3,"The Enchanted Forest","",""),
            new Tale(4,"The Quest for the Magic Sword","",""),
            new Tale(5,"The Secret of the Old Castle","",""),
            new Tale(6,"The Dragon's Lair","",""),
            new Tale(7,"The Magical Journey","",""),
            new Tale(8,"The Princess and the Frog","",""),
            new Tale(9,"The Wizard's Spell","",""),
            new Tale(10, "The Haunted Mansion","","")
    ));
    public List<Tale> getTales() {
        return tales;
    }
    public void editTale(Tale existingTale ,Tale editedTale) {
        tales.set(tales.indexOf(existingTale), editedTale);
    }
    public void deleteTale(Tale tale) {
        tales.remove(tale);
    }
    public void createTale(Tale tale) {
        tales.add(tale);
    }
}
