package com.example.cardealer.service;

import com.example.cardealer.model.dto.SupplierWithCountOfPartsDto;
import com.example.cardealer.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {

    void seedSuppliers() throws IOException;

    Supplier getRandomSupplier();

    List<SupplierWithCountOfPartsDto> findAllLocalSuppliers();
}
