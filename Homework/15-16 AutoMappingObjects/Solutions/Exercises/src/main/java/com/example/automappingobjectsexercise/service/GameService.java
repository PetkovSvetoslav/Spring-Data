package com.example.automappingobjectsexercise.service;

import com.example.automappingobjectsexercise.model.dto.game.DetailedGameDto;
import com.example.automappingobjectsexercise.model.dto.game.GameDto;
import com.example.automappingobjectsexercise.model.dto.game.GameViewDto;

import java.util.List;
import java.util.Map;

public interface GameService {

    String addGame(GameDto gameAddDto);

    String editGame(long id, Map<String, String> fieldNamesAndValues) throws NoSuchFieldException, IllegalAccessException;

    String deleteGame(long id);

    List<GameViewDto> getAllGames();

    DetailedGameDto getGameAsDetailedView(String title);
}
