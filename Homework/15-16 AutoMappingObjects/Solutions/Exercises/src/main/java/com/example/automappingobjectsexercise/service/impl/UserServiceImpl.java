package com.example.automappingobjectsexercise.service.impl;

import com.example.automappingobjectsexercise.model.dto.game.GameTitleDto;
import com.example.automappingobjectsexercise.model.dto.user.UserLoginDto;
import com.example.automappingobjectsexercise.model.dto.user.UserRegistrationDto;
import com.example.automappingobjectsexercise.model.entity.User;
import com.example.automappingobjectsexercise.repository.UserRepository;
import com.example.automappingobjectsexercise.service.UserService;
import com.example.automappingobjectsexercise.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final Validation validation;

    private User loggedInUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper, Validation validation) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.validation = validation;
    }

    @Override
    public String registerUser(UserRegistrationDto userRegistrationDto) {
        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            throw new IllegalArgumentException("The passwords you entered do not match.");
        }

        this.validation.validateEntity(userRegistrationDto);

        User user = this.mapper.map(userRegistrationDto, User.class);

        this.userRepository.save(user);

        return user.getFullName();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        if (this.loggedInUser == null) {
            this.validation.validateEntity(userLoginDto);

            User user = this.userRepository.findByEmailAndPassword(
                    userLoginDto.getEmail(), userLoginDto.getPassword()
            ).orElseThrow(() -> new IllegalArgumentException("Incorrect username / password"));

            this.loggedInUser = user;

            return user.getFullName();
        } else {
            throw new IllegalStateException("There is already logged in user");
        }
    }

    @Override
    public String logout() {
        if (this.loggedInUser != null) {
            String fullName = this.loggedInUser.getFullName();

            this.loggedInUser = null;

            return fullName;
        } else {
            throw new IllegalStateException("Cannot log out. No user was logged in.");
        }
    }

    @Override
    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    @Override
    public boolean isUserLoggedIn() {
        return this.loggedInUser != null;
    }

    @Override
    public boolean isUserAdmin() {
        if (!this.isUserLoggedIn()) {
            throw new IllegalStateException("No user was logged in.");
        }

        return this.loggedInUser.isAdministrator();
    }

    @Override
    public List<GameTitleDto> getOwnedGames() {
        return this.loggedInUser
                .getGames()
                .stream()
                .map(g -> this.mapper.map(g, GameTitleDto.class))
                .collect(Collectors.toList());
    }
}
