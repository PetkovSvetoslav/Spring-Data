package com.example.automappingobjectsexercise.model.dto.game;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameViewDto extends GameTitleDto {
    private BigDecimal price;

    @Override
    public String toString() {
        return super.getTitle() + " " + this.getPrice();
    }
}
