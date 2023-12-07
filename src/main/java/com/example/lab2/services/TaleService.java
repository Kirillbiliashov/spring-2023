package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import java.util.*;

public interface TaleService {
    List<Tale> getAllTales();
    Tale getTaleById(Long id);
    Tale save(Tale tale);
    void deleteTale(Long id);
    List<Tale> findBestTales(int limit);
    void addLikeToTale(Long userId, Long taleId);
    void removeLikeFromTale(Long userId, Long taleId);
}
