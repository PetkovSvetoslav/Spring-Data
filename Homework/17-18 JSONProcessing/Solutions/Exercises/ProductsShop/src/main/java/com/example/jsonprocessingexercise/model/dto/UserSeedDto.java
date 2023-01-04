package com.example.jsonprocessingexercise.model.dto;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class UserSeedDto {

    @Expose
    private String firstName;

    @Expose
    @NotNull
    @Size(min = 3)
    private String lastName;

    @Expose
    private byte age;
}
