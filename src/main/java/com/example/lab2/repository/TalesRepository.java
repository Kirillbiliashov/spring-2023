package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TalesRepository {

    private List<Tale> tales = List.of(
            new Tale(1, "The Adventure of the Lost Key", 2004),
            new Tale(2, "The Mystery of the Hidden Treasure", 1991),
            new Tale(3, "The Enchanted Forest", 2008),
            new Tale(4, "The Quest for the Magic Sword", 1969),
            new Tale(5, "The Secret of the Old Castle", 1987),
            new Tale(6, "The Dragon's Lair", 2016),
            new Tale(7, "The Magical Journey", 2011),
            new Tale(8, "The Princess and the Frog", 1975),
            new Tale(9, "The Wizard's Spell", 2002),
            new Tale(10, "The Haunted Mansion", 2019)
    );

    public List<Tale> getTales() {
        return tales;
    }

    public Tale getTale(int id) {
        return tales.get(id - 1);
    }

     public void addTale(Tale tale) {
         tales.add(tale);
    }

    public void editTale(Tale tale) {
         int idx = tale.getId() - 1;
       tales.remove(idx);
       tales.add(idx, tale);
    }

    public void deleteTale(int id) {
         tales.remove(id - 1);
    }

}
