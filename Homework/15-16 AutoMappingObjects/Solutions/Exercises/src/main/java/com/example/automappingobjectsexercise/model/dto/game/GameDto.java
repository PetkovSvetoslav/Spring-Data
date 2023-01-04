package com.example.automappingobjectsexercise.model.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameDto {
    @Pattern(regexp = "^[A-Z].{2,99}$",
            message = "Invalid title!")
    private String title;
    @DecimalMin(value = "0",
            message = "Price must be a positive number!")
    private BigDecimal price;
    @DecimalMin(value = "0",
            message = "Size must be a positive number!")
    private Double size;
    @Size(min = 11, max = 11,
            message = "The length of the trailer must be exactly 11 characters!")
    private String trailer;
    @Pattern(regexp = "^https?://.*",
            message = "The URl must starts with 'http://' or 'https://'")
    private String thumbnailURL;
    @Size(min = 20,
            message = "The description must be at least 20 symbols!")
    private String description;
    private String releaseDate;
}
