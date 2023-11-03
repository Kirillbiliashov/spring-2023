package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import com.example.lab2.repository.TaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaleService {
    //Напряму у поле біна
    @Autowired
    private TaleRepository taleRepository;
    public List<Tale> getAllTales() {
        return taleRepository.getTales();
    }
    public Tale getTaleById(int id) {
        for (Tale tale : taleRepository.getTales()) {
            if (tale.getId() == id) {
                return tale;
            }
        }
        return null;
    }
    public void createTale(Tale tale) {
        taleRepository.createTale(tale);
    }
    public void editTale(int id, Tale editedTale) {
        Tale existingTale = getTaleById(id);
        if (existingTale != null) {
            taleRepository.editTale(existingTale,editedTale);
        }
    }
    public void deleteTale(int id) {
        taleRepository.deleteTale(getTaleById(id));
    }
}
