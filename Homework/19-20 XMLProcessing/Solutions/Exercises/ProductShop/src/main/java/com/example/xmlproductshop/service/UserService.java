package com.example.xmlproductshop.service;

import com.example.xmlproductshop.model.dto.UserWithProductsDto;
import com.example.xmlproductshop.model.dto.UserWithSoldProductsDto;
import com.example.xmlproductshop.model.dto.seed.UserSeedDto;
import com.example.xmlproductshop.model.entity.User;

import java.util.List;

public interface UserService {

    void seedUsers(List<UserSeedDto> users);

    User getRandomUser();

    List<UserWithSoldProductsDto> findAllUsersWithMoreThanOneSoldProduct();

    List<UserWithProductsDto> findAllUsersWithSoldProducts();
}
