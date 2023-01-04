package com.example.automappingobjectsexercise;

import com.example.automappingobjectsexercise.model.dto.game.GameDto;
import com.example.automappingobjectsexercise.model.dto.game.GameTitleDto;
import com.example.automappingobjectsexercise.model.dto.user.UserLoginDto;
import com.example.automappingobjectsexercise.model.dto.user.UserRegistrationDto;
import com.example.automappingobjectsexercise.service.GameService;
import com.example.automappingobjectsexercise.service.OrderService;
import com.example.automappingobjectsexercise.service.UserService;
import com.example.automappingobjectsexercise.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final BufferedReader reader;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public CommandLineRunnerImpl(BufferedReader reader, UserService userService, GameService gameService, OrderService orderService) {
        this.reader = reader;
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            String[] commands = reader.readLine().split("\\|");
            String fullName;
            String gameTitle;

            try {
                switch (commands[0]) {
                    case "RegisterUser" -> {
                        fullName = this.userService
                                .registerUser(
                                        new UserRegistrationDto(
                                                commands[1],
                                                commands[2],
                                                commands[3],
                                                commands[4]
                                        )
                                );
                        System.out.println(fullName + " was registered");
                    }
                    case "LoginUser" -> {
                        fullName = this.userService
                                .loginUser(
                                        new UserLoginDto(
                                                commands[1],
                                                commands[2]
                                        )
                                );
                        System.out.println("Successfully logged in " + fullName);
                    }
                    case "Logout" -> {
                        fullName = this.userService.logout();
                        System.out.println("User " + fullName + " successfully logged out");
                    }
                    case "AddGame" -> {
                        gameTitle = this.gameService.addGame(
                                new GameDto(
                                        commands[1],
                                        new BigDecimal(commands[2]),
                                        Double.parseDouble(commands[3]),
                                        commands[4],
                                        commands[5],
                                        commands[6],
                                        commands[7]
                                )
                        );

                        System.out.println("Added " + gameTitle);
                    }
                    case "EditGame" -> {
                        gameTitle = this.gameService.editGame(
                                Long.parseLong(commands[1]),
                                Parser.parseMap(
                                        Arrays.stream(commands).skip(2).toArray(String[]::new), "="
                                )
                        );

                        System.out.println("Edited " + gameTitle);
                    }
                    case "DeleteGame" -> {
                        gameTitle = this.gameService.deleteGame(Long.parseLong(commands[1]));

                        System.out.println("Deleted " + gameTitle);
                    }
                    case "AllGames" -> {
                        this.gameService.getAllGames().forEach(System.out::println);
                    }
                    case "DetailGame" -> {
                        gameTitle = commands[1];

                        System.out.println(this.gameService.getGameAsDetailedView(gameTitle));
                    }
                    case "OwnedGames" -> {
                        this.userService.getOwnedGames().forEach(System.out::println);
                    }
                    case "AddItem" -> {
                        gameTitle = commands[1];

                        this.orderService.addItem(gameTitle);

                        System.out.println(gameTitle + " added to the cart.");
                    }
                    case "RemoveItem" -> {
                        gameTitle = commands[1];

                        this.orderService.removeItem(gameTitle);

                        System.out.println(gameTitle + " removed from the cart.");
                    }
                    case "BuyItem" -> {
                        List<GameTitleDto> boughtGames = this.orderService.byItems();

                        System.out.println("Successfully bought games:" + System.lineSeparator() +
                                boughtGames
                                        .stream()
                                        .map(GameTitleDto::getTitle)
                                        .collect(Collectors.joining(System.lineSeparator(), " -", "")));
                    }
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
