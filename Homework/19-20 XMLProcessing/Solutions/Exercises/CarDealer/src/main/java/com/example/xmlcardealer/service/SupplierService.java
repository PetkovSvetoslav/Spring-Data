package com.example.xmlcardealer.service;

import com.example.xmlcardealer.model.dto.SupplierWithCountOfPartsDto;
import com.example.xmlcardealer.model.dto.seed.SupplierSeedDto;
import com.example.xmlcardealer.model.entity.Supplier;

import java.util.List;

public interface SupplierService {

    void seedSuppliers(List<SupplierSeedDto> suppliers);

    Supplier getRandomSupplier();

    List<SupplierWithCountOfPartsDto> findAllLocalSuppliers();
}
