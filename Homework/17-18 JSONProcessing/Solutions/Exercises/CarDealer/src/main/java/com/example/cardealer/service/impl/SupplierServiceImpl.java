package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.SupplierWithCountOfPartsDto;
import com.example.cardealer.model.dto.seed.SupplierSeedDto;
import com.example.cardealer.model.entity.Supplier;
import com.example.cardealer.rapository.SupplierRepository;
import com.example.cardealer.service.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constatnt.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class SupplierServiceImpl implements SupplierService {

    private static final String SUPPLIERS_FILE_NAME = "files/suppliers.json";

    private final SupplierRepository supplierRepository;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper mapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        String content = Files.readString(
                Path.of(RESOURCES_FILE_PATH + SUPPLIERS_FILE_NAME)
        );

        SupplierSeedDto[] supplierSeedDtos = gson.fromJson(content, SupplierSeedDto[].class);

        Arrays.stream(supplierSeedDtos)
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
