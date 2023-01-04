package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.CustomerPurchasesInfoDto;
import com.example.cardealer.model.dto.CustomerWithSalesDto;
import com.example.cardealer.model.dto.seed.CustomerSeedDto;
import com.example.cardealer.model.entity.Customer;
import com.example.cardealer.rapository.CustomerRepository;
import com.example.cardealer.service.CustomerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constatnt.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_FILE_NAME = "files/customers.json";

    private final CustomerRepository customerRepository;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper mapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
        String contents = Files.readString(
                Path.of(RESOURCES_FILE_PATH + CUSTOMER_FILE_NAME)
        );

        CustomerSeedDto[] customerSeedDtos = gson.fromJson(contents, CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .map(customerSeedDto -> mapper.map(customerSeedDto, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {
        long count = this.customerRepository.count();

        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CustomerWithSalesDto> getOrderedCustomers() {
        List<Customer> customers = this.customerRepository
                .findAllOrderByBirthDateAscThenByNotYoungDriver();

        return customers
                .stream()
                .map(customer -> mapper.map(customer, CustomerWithSalesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerPurchasesInfoDto> findAllCustomersWithAtLeastOneCar() {
        return this.customerRepository
                .findAllCustomersWithTheCountOfCarsTheyBoughtAndTheMoneySpent()
                .stream()
                .map(objects -> {
                    CustomerPurchasesInfoDto customerInfo =
                            new CustomerPurchasesInfoDto();

                    customerInfo.setFullName((String) objects[0]);
                    customerInfo.setBoughtCars(Integer.parseInt(String.valueOf(objects[1])));
                    customerInfo.setSpentMoney(new BigDecimal(String.valueOf(objects[2])));

                    return customerInfo;
                })
                .collect(Collectors.toList());
    }
}
