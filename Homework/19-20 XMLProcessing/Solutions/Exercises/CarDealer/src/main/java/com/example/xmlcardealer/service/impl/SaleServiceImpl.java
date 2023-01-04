package com.example.xmlcardealer.service.impl;

import com.example.xmlcardealer.constants.Discounts;
import com.example.xmlcardealer.model.dto.CarDto;
import com.example.xmlcardealer.model.dto.SaleInfoDto;
import com.example.xmlcardealer.model.entity.Car;
import com.example.xmlcardealer.model.entity.Customer;
import com.example.xmlcardealer.model.entity.Sale;
import com.example.xmlcardealer.repository.SaleRepository;
import com.example.xmlcardealer.service.CarService;
import com.example.xmlcardealer.service.CustomerService;
import com.example.xmlcardealer.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarService carService,
                           CustomerService customerService) {

        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
    }

    @Override
    public void generateRandomSales(int countOfSales) {
        for (int i = 0; i < countOfSales; i++) {
            Car randomCar = carService.getRandomCar();
            Customer randomCustomer = customerService.getRandomCustomer();
            BigDecimal discount = this.getRandomPercentage();

            Sale sale = new Sale(randomCar, randomCustomer, discount);

            this.saleRepository.save(sale);
        }
    }

    private BigDecimal getRandomPercentage() {
        int countOfValues = Discounts.getSize();

        int randomIndex = ThreadLocalRandom.current().nextInt(countOfValues);
        Discounts[] percentages = Discounts.values();

        return percentages[randomIndex].getDecimal();
    }

    @Override
    public List<SaleInfoDto> getSalesInfo() {
        return this.saleRepository
                .findAllSalesAsInfo()
                .stream()
                .map(this::mapToSaleInfoDto)
                .collect(Collectors.toList());
    }

    private SaleInfoDto mapToSaleInfoDto(Object[] objects) {
        CarDto car = new CarDto();

        car.setMake((String) objects[0]);
        car.setModel((String) objects[1]);
        car.setTravelledDistance(Long.parseLong(String.valueOf(objects[2])));

        SaleInfoDto sale = new SaleInfoDto();
        sale.setCar(car);

        sale.setCustomerName((String) objects[3]);
        sale.setDiscount(new BigDecimal(String.valueOf(objects[4])));
        sale.setPrice(new BigDecimal(String.valueOf(objects[5])));
        sale.setPriceWithDiscount(new BigDecimal(String.valueOf(objects[6])));

        return sale;
    }
}
