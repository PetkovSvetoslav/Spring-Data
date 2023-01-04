package com.example.automappingobjectsexercise.service.impl;

import com.example.automappingobjectsexercise.model.dto.game.GameTitleDto;
import com.example.automappingobjectsexercise.model.entity.Game;
import com.example.automappingobjectsexercise.model.entity.Order;
import com.example.automappingobjectsexercise.model.entity.User;
import com.example.automappingobjectsexercise.repository.GameRepository;
import com.example.automappingobjectsexercise.repository.OrderRepository;
import com.example.automappingobjectsexercise.repository.UserRepository;
import com.example.automappingobjectsexercise.service.OrderService;
import com.example.automappingobjectsexercise.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper mapper;

    @Autowired
    public OrderServiceImpl(UserService userService, GameRepository gameRepository, UserRepository userRepository, OrderRepository orderRepository, ModelMapper mapper) {
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    /**
     * Adds a game to the current open order, if there is non, it will open a new one.
     * If there is no logged-in user or no game with such a title in the database it will throw an exception.
     * If the user alreday has this game an exception will be thrown.
     *
     * @param gameTitle
     */
    @Transactional
    @Override
    public void addItem(String gameTitle) {
        ensureTheUserIsLoggedIn();
        ensureThatThereIsAGameWithSuchATitle(gameTitle);

        User buyer = this.userService.getLoggedInUser();
        Game game = this.gameRepository.findByTitle(gameTitle).get();

        if (buyer.getGames().contains(game)) {
            throw new IllegalStateException("You already own " + gameTitle);
        }

        Order order = this.orderRepository.findByBuyerAndBoughtFalse(buyer).orElse(new Order());

        order.setBuyer(buyer);
        order.getProducts().add(game);

        this.orderRepository.save(order);
    }

    /**
     * Removes the game with the corresponding title from the curren open order.
     * If there is no logged-in user or no game with such a title in the database it will throw an exception.
     * If there is no game with such a name in the order it will throw an exception again.
     *
     * @param gameTitle
     */
    @Transactional
    @Override
    public void removeItem(String gameTitle) {
        this.ensureTheUserIsLoggedIn();
        this.ensureThatThereIsAGameWithSuchATitle(gameTitle);

        User buyer = this.userService.getLoggedInUser();
        Game game = this.gameRepository.findByTitle(gameTitle).get();

        Order order = this.orderRepository.findByBuyerAndBoughtFalse(buyer).orElse(null);

        if (order == null || !order.getProducts().remove(game)) {
            throw new IllegalArgumentException("There is no such game in the shopping cart");
        }

        this.orderRepository.save(order);
    }

    /**
     * If there is no logged-in user it will throw an exception.
     *
     * @return the items in the current open order and then closes it.
     */
    @Transactional
    @Override
    public List<GameTitleDto> byItems() {
        this.ensureTheUserIsLoggedIn();

        User buyer = this.userService.getLoggedInUser();

        Order order = this.orderRepository.findByBuyerAndBoughtFalse(buyer)
                .orElseThrow(() -> new IllegalStateException("The shopping cart is empty"));

        buyer.getGames().addAll(order.getProducts());
        this.userRepository.save(buyer);

        order.setBought(true);
        this.orderRepository.save(order);

        return order
                .getProducts()
                .stream()
                .map(g -> mapper.map(g, GameTitleDto.class))
                .collect(Collectors.toList());
    }

    private void ensureThatThereIsAGameWithSuchATitle(String gameTitle) {
        if (this.gameRepository.findByTitle(gameTitle).isEmpty()) {
            throw new IllegalArgumentException("There is no game with such a title");
        }
    }

    private void ensureTheUserIsLoggedIn() {
        if (!this.userService.isUserLoggedIn()) {
            throw new IllegalStateException("No user was logged in.");
        }
    }
}
