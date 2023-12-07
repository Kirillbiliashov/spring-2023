package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import com.example.lab2.repository.InMemoryTaleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaleServiceImpl implements TaleService {
    private final InMemoryTaleRepository taleRepository;

    public TaleServiceImpl(InMemoryTaleRepository taleRepository) {
        this.taleRepository = taleRepository;
    }
    @Override
    public List<Tale> getAllTales() {
        return taleRepository.findAll();
    }
    @Override
    public Tale getTaleById(Long id) {
        return taleRepository.findById(id);
    }
    @Override
    public Tale save(Tale tale) {
        return taleRepository.save(tale);
    }
    @Override
    public void deleteTale(Long id) {
        taleRepository.deleteById(id);
    }
    @Override
    public List<Tale> findBestTales(int limit) {
        List<Tale> sortedTales = new ArrayList<>(taleRepository.findAll());
        sortedTales.sort(Comparator.comparingInt(tale -> tale.getLikes().size()));
        Collections.reverse(sortedTales);

        return sortedTales.stream().limit(limit).collect(Collectors.toList());
    }
    @Override
    public void addLikeToTale(Long userId, Long taleId) {
        Tale tale = getTaleById(taleId);
        tale.getLikes().add(userId);
        taleRepository.save(tale);
    }
    @Override
    public void removeLikeFromTale(Long userId, Long taleId) {
        Tale tale = getTaleById(taleId);

        if (tale != null) {
            tale.getLikes().remove(userId);
            taleRepository.save(tale);
        }
    }
}
