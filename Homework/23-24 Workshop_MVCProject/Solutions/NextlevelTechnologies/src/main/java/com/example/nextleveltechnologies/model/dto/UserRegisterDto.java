package com.example.nextleveltechnologies.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class UserRegisterDto {

    @NotBlank
    private String username;

    private String password;

    @NotBlank
    private String confirmPassword;

    private String email;
}
