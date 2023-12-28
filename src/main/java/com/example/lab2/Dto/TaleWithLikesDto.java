package com.example.lab2.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaleWithLikesDto {
    private Long id;
    private String title;
    private String author;
    private int likes;
}