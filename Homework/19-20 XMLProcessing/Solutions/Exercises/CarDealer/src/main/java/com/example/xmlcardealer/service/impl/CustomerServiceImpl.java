package com.example.xmlcardealer.service.impl;

import com.example.xmlcardealer.model.dto.CustomerPurchasesInfoDto;
import com.example.xmlcardealer.model.dto.CustomerWithSalesDto;
import com.example.xmlcardealer.model.dto.seed.CustomerSeedDto;
import com.example.xmlcardealer.model.entity.Customer;
import com.example.xmlcardealer.repository.CustomerRepository;
import com.example.xmlcardealer.service.CustomerService;
import com.example.xmlcardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ValidationUtil validationUtil, ModelMapper mapper) {

        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedCustomers(Collection<CustomerSeedDto> customers) {
        customers
                .stream()
                .filter(validationUtil::isValid)
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
