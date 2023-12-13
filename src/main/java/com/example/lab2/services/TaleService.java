package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import java.util.*;

public interface TaleService {
    List<Tale> findAll();
    Tale findById(Long id);
    Tale create(Tale tale);
    Tale update(Tale tale);
    void deleteById(Long id);
    List<Tale> findBestTales();
    void addReadToTale(Long userId, Long taleId);
    void removeReadFromTale(Long userId, Long taleId);
    void addLikeToTale(Long userId, Long taleId);
    void removeLikeFromTale(Long userId, Long taleId);
    List<Tale> findFavoriteTalesByUserId(Long userId);
    List<Tale> findUnreadTalesByUserId(Long userId);

    Collection<Tale> findByCriteria(String criteria);

}
