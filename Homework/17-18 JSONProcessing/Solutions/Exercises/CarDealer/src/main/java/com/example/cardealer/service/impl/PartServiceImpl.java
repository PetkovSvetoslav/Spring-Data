package com.example.cardealer.service.impl;

import com.example.cardealer.model.dto.seed.PartSeedDto;
import com.example.cardealer.model.entity.Part;
import com.example.cardealer.rapository.PartRepository;
import com.example.cardealer.service.PartService;
import com.example.cardealer.service.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.constatnt.GlobalConstant.RESOURCES_FILE_PATH;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_FILE_NAME = "files/parts.json";

    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private final ModelMapper mapper;
    private final Gson gson;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierService supplierService, ModelMapper mapper, Gson gson) {
        this.partRepository = partRepository;
        this.supplierService = supplierService;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedParts() throws IOException {
        String content = Files.readString(
                Path.of(RESOURCES_FILE_PATH + PARTS_FILE_NAME)
        );

        PartSeedDto[] partSeedDtos = gson.fromJson(content, PartSeedDto[].class);

        List<Part> parts = Arrays.stream(partSeedDtos)
                .map(partSeedDto -> mapper.map(partSeedDto, Part.class))
                .collect(Collectors.toList());

        for (Part part : parts) {
            part.setSupplier(this.supplierService.getRandomSupplier());
            this.partRepository.save(part);
        }
    }

    @Override
    public Set<Part> getRandomParts(int lower, int upper) {
        long count = this.partRepository.count();

        if (count < upper) {
            throw new IllegalArgumentException(
                    "The upper bound cannot be grater than the number of parts."
            );
        }

        int randomSize = ThreadLocalRandom.current().nextInt(lower, upper + 1);

        Set<Part> parts = new HashSet<>();

        while (parts.size() < randomSize) {

            long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);
            Part part = this.partRepository.findById(randomId).orElse(null);

            parts.add(part);
        }

        return parts;
    }
}
