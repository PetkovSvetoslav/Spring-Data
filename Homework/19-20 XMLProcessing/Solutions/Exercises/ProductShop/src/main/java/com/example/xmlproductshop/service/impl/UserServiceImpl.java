package com.example.xmlproductshop.service.impl;

import com.example.xmlproductshop.model.dto.UserWithProductsDto;
import com.example.xmlproductshop.model.dto.UserWithSoldProductsDto;
import com.example.xmlproductshop.model.dto.seed.UserSeedDto;
import com.example.xmlproductshop.model.entity.Product;
import com.example.xmlproductshop.model.entity.User;
import com.example.xmlproductshop.repository.ProductRepository;
import com.example.xmlproductshop.repository.UserRepository;
import com.example.xmlproductshop.service.UserService;
import com.example.xmlproductshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository, ValidationUtil validationUtil, ModelMapper mapper) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users
                .stream()
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
    public List<UserWithSoldProductsDto> findAllUsersWithMoreThanOneSoldProduct() {
        List<User> usersWithSoldProducts = userRepository
                .findAllWithMoreThanOneSoldProductOrderedByLastNameThenFirstName();

        for (User user : usersWithSoldProducts) {
            Set<Product> soldProducts = productRepository
                    .findAllBySellerAndBuyerNotNull(user);

            user.setProducts(soldProducts);
        }

        return usersWithSoldProducts
                .stream()
                .map(user -> mapper.map(user, UserWithSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWithProductsDto> findAllUsersWithSoldProducts() {
        List<User> users = userRepository
                .findAllWithMoreThanOneSoldProduct();

        for (User user : users) {
            Set<Product> soldProducts = productRepository
                    .findAllBySellerAndBuyerNotNull(user);

            user.setProducts(soldProducts);
        }

        return users
                .stream()
                .map(user -> mapper.map(user, UserWithProductsDto.class))
                .collect(Collectors.toList());
    }
}
