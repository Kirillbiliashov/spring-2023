package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;

import java.util.Collection;



public interface TaleRepository {
    Tale findById(Long id);
    Collection<Tale> findAll();
    Long create(Tale tale);
    Tale update(Tale tale);
    void deleteById(Long id);

    Collection<Tale> findTaleByCriteria(String criteria);

}
