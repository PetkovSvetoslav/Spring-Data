package com.example.jsonprocessingexercise.service.impl;

import com.example.jsonprocessingexercise.model.dto.UserSeedDto;
import com.example.jsonprocessingexercise.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.ProductDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.SoldProductsDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.UserDto;
import com.example.jsonprocessingexercise.model.dto.users_and_products.UsersCountDto;
import com.example.jsonprocessingexercise.model.entity.Product;
import com.example.jsonprocessingexercise.model.entity.User;
import com.example.jsonprocessingexercise.repository.ProductRepository;
import com.example.jsonprocessingexercise.repository.UserRepository;
import com.example.jsonprocessingexercise.service.UserService;
import com.example.jsonprocessingexercise.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.jsonprocessingexercise.constant.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {

    public static final String USERS_FILE_NAME = "users.json";

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public UserServiceImpl(UserRepository userRepository, ProductRepository productRepository, ModelMapper mapper,
                           ValidationUtil validationUtil, Gson gson) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public void seedUsers() throws IOException {
        String fileContents = Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME));

        UserSeedDto[] userSeedDtos = gson.fromJson(fileContents, UserSeedDto[].class);

        Arrays.stream(userSeedDtos)
                .filter(validationUtil::isValid)
                .map(userSeedDto -> mapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {
        long usersTotalCount = this.userRepository.count();

        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1, usersTotalCount + 1);

        return this.userRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<UserWithSoldProductsDto> findAllSellersWithMoreThanOneSoldProduct() {
        return this.userRepository
                .findAllWithMoreThanOneSoldProductOrderedByLastNameThenFirstName()
                .stream()
                .map(user -> mapper.map(user, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersCountDto findAllSellersWithTheirSoldProducts() {
        List<User> users = userRepository.findAllWithMoreThanOneSoldProduct();

        UsersCountDto usersCountDto = new UsersCountDto();

        // Add the number of users
        usersCountDto.setUsersCount(users.size());

        List<UserDto> userDtoList = new ArrayList<>();
        usersCountDto.setUsers(userDtoList);

        for (User user : users) {
            // Add user
            UserDto userDto = mapper.map(user, UserDto.class);
            userDtoList.add(userDto);

            // Find all sold products from this user
            List<Product> soldProducts = this.productRepository.findAllBySellerAndBuyerNotNull(user);

            // Move to a DTO
            SoldProductsDto soldProductsDto = new SoldProductsDto();
            userDto.setSoldProducts(soldProductsDto);

            // Add the count of the products
            soldProductsDto.setCount(soldProducts.size());

            // Add the products
            soldProductsDto.setProducts(
                    soldProducts
                            .stream()
                            .map(product -> mapper.map(product, ProductDto.class))
                            .collect(Collectors.toList())
            );
        }

        return usersCountDto;
    }
}
