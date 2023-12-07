package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;



public interface TaleRepository {
    Tale findById(Long id);
    Collection<Tale> findAll();
    Tale save(Tale tale);
    void deleteById(Long id);

    Page<Tale> findTalesPageWithFilter(PageRequest pageRequest, String filter);
}
