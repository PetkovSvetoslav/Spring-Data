package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.CarWithIdDto;
import com.example.cardealer.model.dto.CarWithPartsDto;
import com.example.cardealer.model.dto.seed.CarSeedDto;
import com.example.cardealer.model.entity.Car;
import com.example.cardealer.model.entity.Part;
import com.example.cardealer.rapository.CarRepository;
import com.example.cardealer.service.CarService;
import com.example.cardealer.service.PartService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constatnt.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_NAME = "files/cars.json";

    private static final int LOWER_BOUND = 3;
    private static final int UPPER_BOUND = 5;

    private final CarRepository carRepository;
    private final PartService partService;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, PartService partService, ModelMapper mapper, Gson gson) {
        this.carRepository = carRepository;
        this.partService = partService;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedCars() throws IOException {
        String content = Files.readString(
                Path.of(RESOURCES_FILE_PATH + CARS_FILE_NAME)
        );

        CarSeedDto[] carSeedDtos = gson.fromJson(content, CarSeedDto[].class);

        List<Car> cars = Arrays.stream(carSeedDtos)
                .map(carSeedDto -> mapper.map(carSeedDto, Car.class))
                .collect(Collectors.toList());

        for (Car car : cars) {
            Set<Part> randomParts = this.partService.getRandomParts(LOWER_BOUND, UPPER_BOUND);
            car.setParts(randomParts);

            this.carRepository.save(car);
        }
    }

    @Override
    public Car getRandomCar() {
        long count = this.carRepository.count();

        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarWithIdDto> findAllByMake(String make) {
        return this.carRepository
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
