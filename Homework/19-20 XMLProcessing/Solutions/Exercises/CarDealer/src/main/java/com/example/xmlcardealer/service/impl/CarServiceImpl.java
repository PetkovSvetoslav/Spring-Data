package com.example.xmlcardealer.service.impl;

import com.example.xmlcardealer.model.dto.CarWithIdDto;
import com.example.xmlcardealer.model.dto.CarWithPartsDto;
import com.example.xmlcardealer.model.dto.seed.CarSeedDto;
import com.example.xmlcardealer.model.entity.Car;
import com.example.xmlcardealer.model.entity.Part;
import com.example.xmlcardealer.repository.CarRepository;
import com.example.xmlcardealer.service.CarService;
import com.example.xmlcardealer.service.PartService;
import com.example.xmlcardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final int LOWER_BOUND = 10;
    private static final int UPPER_BOUND = 20;

    private final CarRepository carRepository;
    private final PartService partService;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartService partService,
                          ValidationUtil validationUtil, ModelMapper mapper) {

        this.carRepository = carRepository;
        this.partService = partService;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedCars(Collection<CarSeedDto> cars) {
        cars
                .stream()
                .filter(validationUtil::isValid)
                .map(carSeedDto -> mapper.map(carSeedDto, Car.class))
                .forEach(car -> {
                    Set<Part> randomParts = partService.getRandomParts(LOWER_BOUND, UPPER_BOUND);
                    car.setParts(randomParts);

                    carRepository.save(car);
                });
    }

    @Override
    public Car getRandomCar() {
        long count = this.carRepository.count();

        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarWithIdDto> findAllByMake(String make) {
        return carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                .stream()
                .map(car -> mapper.map(car, CarWithIdDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarWithPartsDto> findAllCarsWithTheirParts() {
        return this.carRepository
                .findAll()
                .stream()
                .map(car -> mapper.map(car, CarWithPartsDto.class))
                .collect(Collectors.toList());
    }
}
