package com.example.automappingobjectsexercise.model.dto.game;

import lombok.Data;

@Data
public class GameTitleDto {
    String title;

    @Override
    public String toString() {
        return this.getTitle();
    }
}
