package com.example.xmlcardealer.service;

import com.example.xmlcardealer.model.dto.SaleInfoDto;

import java.util.List;

public interface SaleService {

    void generateRandomSales(int countOfSales);

    List<SaleInfoDto> getSalesInfo();
}
