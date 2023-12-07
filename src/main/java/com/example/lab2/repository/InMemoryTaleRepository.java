package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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


    @Override
    public Page<Tale> findTalesPageWithFilter(PageRequest pageRequest, String filter) {
        List<Tale> allTales = new ArrayList<>(tales.values());

        List<Tale> filteredTales = allTales;
        if (filter != null && !filter.isBlank()) {
            filteredTales = allTales.stream()
                    .filter(tale -> taleMatchesFilter(tale, filter))
                    .collect(Collectors.toList());
        }

        int pageSize = pageRequest.getPageSize();
        int currentPage = pageRequest.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Tale> pagedTales;

        if (filteredTales.size() < startItem) {
            pagedTales = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, filteredTales.size());
            pagedTales = filteredTales.subList(startItem, toIndex);
        }

        Sort sort = pageRequest.getSort();
        if (sort.isSorted()) {
            pagedTales.sort((tale1, tale2) -> {
                int result = 0;
                for (Sort.Order order : sort) {
                    if (order.getProperty().equals("id")) {
                        result = tale1.getId().compareTo(tale2.getId());
                    } else if (order.getProperty().equals("title")) {
                        result = tale1.getTitle().compareTo(tale2.getTitle());
                    } else if (order.getProperty().equals("author")) {
                        result = tale1.getAuthor().compareTo(tale2.getAuthor());
                    }
                    if (order.getDirection().equals(Sort.Direction.DESC)) {
                        result = -result;
                    }
                    if (result != 0) {
                        break;
                    }
                }
                return result;
            });
        }
        return new PageImpl<>(pagedTales, pageRequest, filteredTales.size());
    }

    private boolean taleMatchesFilter(Tale tale, String filter) {
        return tale.getTitle().contains(filter) || tale.getAuthor().contains(filter);
    }

}
