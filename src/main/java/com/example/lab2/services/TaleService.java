package com.example.lab2.services;

import com.example.lab2.Dto.TaleDto;
import com.example.lab2.Dto.TaleWithLikesDto;

import java.util.*;

public interface TaleService {
    List<TaleDto> findAll();
    Optional<TaleDto> findById(Long id);
    TaleDto save(TaleDto taleDto);
    void deleteById(Long id);
    List<TaleWithLikesDto> findBestTales();
    List<TaleDto> findFavoriteTalesByUserId(Long userId);
    List<TaleDto> findUnreadTalesByUserId(Long userId);
    List<TaleDto> findByCriteria(String criteria);

}
