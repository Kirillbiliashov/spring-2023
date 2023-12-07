package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryTaleRepository implements TaleRepository {
    TreeMap<Long, Tale> tales = new TreeMap<>();
    @Override
    public Tale findById(Long id) {
        return tales.get(id);
    }

    @Override
    public List<Tale> findAll() {
        return tales.values().stream().toList();
    }

    @Override
    public Tale save(Tale tale) {
        if(tale.getId() == null) {
        // for HTTP POST:
            Long id = tales.lastKey() + 1;
            tale = new Tale(id, tale.getTitle(), tale.getAuthor());
        }
        // for HTTP PUT & POST:
        tales.put(tale.getId(), tale);
        return tale;
    }

    @Override
    public void deleteById(Long id) {
        tales.remove(id);
    }

    public InMemoryTaleRepository() {
        // Test Data
        tales.put(1L, new Tale(1L, "The Adventure of the Lost Key", "Author1"));
        tales.put(2L, new Tale(2L, "The Mystery of the Hidden Treasure", "Author2"));
        tales.put(3L, new Tale(3L, "The Enchanted Forest", "Author3"));
        tales.put(4L, new Tale(4L, "The Quest for the Magic Sword", "Author4"));
        tales.put(5L, new Tale(5L, "The Secret of the Old Castle", "Author5"));
        tales.put(6L, new Tale(6L, "The Dragon's Lair", "Author6"));
        tales.put(7L, new Tale(7L, "The Magical Journey", "Author7"));
        tales.put(8L, new Tale(8L, "The Princess and the Frog", "Author8"));
        tales.put(9L, new Tale(9L, "The Wizard's Spell", "Author9"));
        tales.put(10L, new Tale(10L, "The Haunted Mansion", "Author10"));
    }
}
