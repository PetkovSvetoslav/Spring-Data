package com.example.automappingobjectsexercise.service;

import com.example.automappingobjectsexercise.model.dto.game.GameTitleDto;
import com.example.automappingobjectsexercise.model.dto.user.UserLoginDto;
import com.example.automappingobjectsexercise.model.dto.user.UserRegistrationDto;
import com.example.automappingobjectsexercise.model.entity.User;

import java.util.List;

public interface UserService {
    String registerUser(UserRegistrationDto user);

    String loginUser(UserLoginDto userLoginDto);

    String logout();

    User getLoggedInUser();

    boolean isUserLoggedIn();

    boolean isUserAdmin();

    List<GameTitleDto> getOwnedGames();
}
