package com.example.nextleveltechnologies.service;

import com.example.nextleveltechnologies.model.dto.UserLoginDto;
import com.example.nextleveltechnologies.model.dto.UserRegisterDto;

public interface UserService {

    boolean registerUser(UserRegisterDto user);

    Long validateUserLoginDetails(UserLoginDto user);
}
