package com.example.jsonprocessingexercise.service;

import com.example.jsonprocessingexercise.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.UsersCountDto;
import com.example.jsonprocessingexercise.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers() throws IOException;

    User getRandomUser();

    List<UserWithSoldProductsDto> findAllSellersWithMoreThanOneSoldProduct();

    UsersCountDto findAllSellersWithTheirSoldProducts();
}
