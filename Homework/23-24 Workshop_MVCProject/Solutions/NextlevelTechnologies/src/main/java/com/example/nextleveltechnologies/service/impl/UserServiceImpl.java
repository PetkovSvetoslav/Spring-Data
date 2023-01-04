package com.example.nextleveltechnologies.service.impl;

import com.example.nextleveltechnologies.model.dto.UserLoginDto;
import com.example.nextleveltechnologies.model.dto.UserRegisterDto;
import com.example.nextleveltechnologies.model.entity.User;
import com.example.nextleveltechnologies.repository.UserRepository;
import com.example.nextleveltechnologies.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean registerUser(UserRegisterDto userRequest) {
        if (userRepository.existsByUsernameOrEmail(
                userRequest.getUsername(), userRequest.getEmail())) {
            return false;
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            return false;
        }

        User user = mapper.map(userRequest, User.class);
        userRepository.save(user);

        return true;
    }

    @Override
    public Long validateUserLoginDetails(UserLoginDto userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getUsername());

        if (user.isPresent()
                && userRequest.getPassword().equals(user.get().getPassword())) {

            return user.get().getId();
        }

        return null;
    }
}
