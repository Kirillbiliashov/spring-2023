package com.example.lab2.Dto;

import lombok.Data;

@Data
public class AuthorDto {
    Long id;
    String name;

    public AuthorDto(String name) {
        this.name = name;
    }
}