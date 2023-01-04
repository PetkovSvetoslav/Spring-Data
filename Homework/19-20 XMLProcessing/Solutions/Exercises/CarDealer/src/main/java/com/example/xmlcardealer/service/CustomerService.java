package com.example.xmlcardealer.service;

import com.example.xmlcardealer.model.dto.CustomerPurchasesInfoDto;
import com.example.xmlcardealer.model.dto.CustomerWithSalesDto;
import com.example.xmlcardealer.model.dto.seed.CustomerSeedDto;
import com.example.xmlcardealer.model.entity.Customer;

import java.util.Collection;
import java.util.List;

public interface CustomerService {

    void seedCustomers(Collection<CustomerSeedDto> customers);

    Customer getRandomCustomer();

    List<CustomerWithSalesDto> getOrderedCustomers();

    List<CustomerPurchasesInfoDto> findAllCustomersWithAtLeastOneCar();
}
