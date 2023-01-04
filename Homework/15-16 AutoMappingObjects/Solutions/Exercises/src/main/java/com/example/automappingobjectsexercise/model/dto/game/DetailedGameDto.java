package com.example.automappingobjectsexercise.model.dto.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class DetailedGameDto extends GameViewDto {
    private String description;
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return "Title: " + super.getTitle() + System.lineSeparator() +
                "Price: " + super.getPrice() + System.lineSeparator() +
                "Description: " + this.getDescription() + System.lineSeparator() +
                "Release date: " + this.getReleaseDate();
    }
}
