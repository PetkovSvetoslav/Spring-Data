package com.example.xmlcardealer.service.impl;

import com.example.xmlcardealer.model.dto.SupplierWithCountOfPartsDto;
import com.example.xmlcardealer.model.dto.seed.SupplierSeedDto;
import com.example.xmlcardealer.model.entity.Supplier;
import com.example.xmlcardealer.repository.SupplierRepository;
import com.example.xmlcardealer.service.SupplierService;
import com.example.xmlcardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ValidationUtil validationUtil, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedSuppliers(List<SupplierSeedDto> suppliers) {
        suppliers
                .stream()
                .filter(validationUtil::isValid)
                .map(supplierSeedDto -> mapper.map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);
    }

    @Override
    public Supplier getRandomSupplier() {
        long count = this.supplierRepository.count();

        long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);

        return this.supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<SupplierWithCountOfPartsDto> findAllLocalSuppliers() {
        return this.supplierRepository
                .findAllLocalSupplierWithTheNumberOfPartsTheySupply()
                .stream()
                .map(objects -> {
                    SupplierWithCountOfPartsDto supplier = new SupplierWithCountOfPartsDto();

                    supplier.setId(Long.parseLong(String.valueOf(objects[0])));
                    supplier.setName((String) objects[1]);
                    supplier.setPartsCount(Integer.parseInt(String.valueOf(objects[2])));

                    return supplier;
                })
                .collect(Collectors.toList());
    }
}
