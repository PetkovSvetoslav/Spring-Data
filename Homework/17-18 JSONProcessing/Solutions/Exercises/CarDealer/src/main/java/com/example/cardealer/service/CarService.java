package com.example.cardealer.service;

import com.example.cardealer.model.dto.CarWithIdDto;
import com.example.cardealer.model.dto.CarWithPartsDto;
import com.example.cardealer.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {

    void seedCars() throws IOException;

    Car getRandomCar();

    List<CarWithIdDto> findAllByMake(String make);

    List<CarWithPartsDto> findAllCarsWithTheirParts();
}
