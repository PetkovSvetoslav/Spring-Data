package com.example.cardealer.service;

import com.example.cardealer.model.dto.SaleInfoDto;

import java.util.List;

public interface SaleService {

    void generateRandomSales(int countOfSales);

    List<SaleInfoDto> getSalesInfo();
}
