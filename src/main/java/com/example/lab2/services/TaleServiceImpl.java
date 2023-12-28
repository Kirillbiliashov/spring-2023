package com.example.lab2.services;

import com.example.lab2.Dto.AuthorDto;
import com.example.lab2.Dto.TaleDto;
import com.example.lab2.Dto.TaleWithLikesDto;
import com.example.lab2.entity.Author;
import com.example.lab2.entity.Tale;
import com.example.lab2.entity.User;
import com.example.lab2.repository.AuthorRepository;
import com.example.lab2.repository.TaleRepository;
import com.example.lab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaleServiceImpl implements TaleService {
    private final TaleRepository taleRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TaleDto> findAll() {
        try {
            List<Tale> tales = taleRepository.findAll();
            return tales.stream()
                    .map(tale -> modelMapper.map(tale, TaleDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving all tales", e);
        }
    }

    @Override
    public Optional<TaleDto> findById(Long id) {
        return taleRepository.findById(id).map(tale -> modelMapper.map(tale, TaleDto.class));
    }

    @Override
    @Transactional
    public TaleDto save(TaleDto taleDto) {
        try {
            Tale tale = modelMapper.map(taleDto, Tale.class);

            Author existingAuthor = authorRepository.findByName(taleDto.getAuthor());

            if (existingAuthor != null) {
                tale.setAuthor(existingAuthor);
            } else {
                AuthorDto newAuthor = new AuthorDto(taleDto.getAuthor());
                Author author = modelMapper.map(newAuthor, Author.class);
                Author savedAuthor = authorRepository.save(author);
                tale.setAuthor(savedAuthor);
            }

            Tale savedTale = taleRepository.save(tale);
            return modelMapper.map(savedTale, TaleDto.class);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while saving tale", e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            taleRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while deleting tale by id=" + id, e);
        }
    }

    @Override
    public List<TaleDto> findByCriteria(String criteria) {
        try {
            List<Tale> tales = taleRepository.findTaleByCriteria(criteria);
            return tales.stream()
                    .map(tale -> modelMapper.map(tale, TaleDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while finding tales by criteria", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaleWithLikesDto> findBestTales() {
        try {
            Pageable pageable = PageRequest.of(0, 3);
            Page<Tale> talePage = taleRepository.findBestTales(pageable);
            List<Tale> tales = talePage.getContent();

            return tales.stream()
                    .map(tale -> new TaleWithLikesDto(
                            tale.getId(),
                            tale.getTitle(),
                            tale.getAuthor().getName(),
                            tale.getLikedByUsers().size())
                    )
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving best tales", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaleDto> findFavoriteTalesByUserId(Long userId) {
        try {
            List<Tale> tales = taleRepository.findFavoriteTalesByUserId(userId);
            return tales.stream()
                    .map(tale -> modelMapper.map(tale, TaleDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving favorite tales by user ID", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaleDto> findUnreadTalesByUserId(Long userId) {
        try {
            List<Tale> tales = taleRepository.findUnreadTalesByUserId(userId);
            return tales.stream()
                    .map(tale -> modelMapper.map(tale, TaleDto.class))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Error while retrieving unread tales by user ID", e);
        }
    }

    @Transactional
    public void updateUserInteraction(Long userId, Long taleId, boolean like, boolean read) {
        Tale tale = taleRepository.findById(taleId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Tale id=" + taleId + " not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("User id=" + userId + " not found"));

        if (like) {
            tale.getLikedByUsers().add(user);
        } else {
            tale.getLikedByUsers().remove(user);
        }

        if (read) {
            tale.getReadByUsers().add(user);
        } else {
            tale.getReadByUsers().remove(user);
        }

        taleRepository.save(tale);
    }
}
