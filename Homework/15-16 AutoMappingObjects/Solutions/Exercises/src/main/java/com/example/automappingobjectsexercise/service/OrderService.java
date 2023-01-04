package com.example.automappingobjectsexercise.service;

import com.example.automappingobjectsexercise.model.dto.game.GameTitleDto;
import com.example.automappingobjectsexercise.model.entity.Game;

import java.util.List;

public interface OrderService {

    void addItem(String gameTitle);

    void removeItem(String gameTitle);

    List<GameTitleDto> byItems();
}
