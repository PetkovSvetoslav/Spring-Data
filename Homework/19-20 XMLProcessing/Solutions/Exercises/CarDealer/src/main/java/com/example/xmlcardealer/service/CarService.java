package com.example.xmlcardealer.service;

import com.example.xmlcardealer.model.dto.CarWithIdDto;
import com.example.xmlcardealer.model.dto.CarWithPartsDto;
import com.example.xmlcardealer.model.dto.seed.CarSeedDto;
import com.example.xmlcardealer.model.entity.Car;

import java.util.Collection;
import java.util.List;

public interface CarService {

    void seedCars(Collection<CarSeedDto> cars);

    Car getRandomCar();

    List<CarWithIdDto> findAllByMake(String make);

    List<CarWithPartsDto> findAllCarsWithTheirParts();
}
