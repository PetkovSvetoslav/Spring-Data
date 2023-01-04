package com.example.xmlcardealer.service.impl;

import com.example.xmlcardealer.model.dto.seed.PartSeedDto;
import com.example.xmlcardealer.model.entity.Part;
import com.example.xmlcardealer.repository.PartRepository;
import com.example.xmlcardealer.service.PartService;
import com.example.xmlcardealer.service.SupplierService;
import com.example.xmlcardealer.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierService supplierService;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierService supplierService,
                           ValidationUtil validationUtil, ModelMapper mapper) {

        this.partRepository = partRepository;
        this.supplierService = supplierService;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public void seedParts(Collection<PartSeedDto> parts) {
        parts
                .stream()
                .filter(validationUtil::isValid)
                .map(partSeedDto -> mapper.map(partSeedDto, Part.class))
                .forEach(part -> {
                    part.setSupplier(supplierService.getRandomSupplier());
                    partRepository.save(part);
                });
    }

    @Override
    public Set<Part> getRandomParts(int lower, int upper) {
        long count = partRepository.count();

        if (count < upper) {
            throw new IllegalArgumentException(
                    "the upper bound cannot be grater than the number of parts."
            );
        }

        int randomSize = ThreadLocalRandom.current().nextInt(lower, upper + 1);

        Set<Part> parts = new HashSet<>();

        while (parts.size() < randomSize) {

            long randomId = ThreadLocalRandom.current().nextLong(1, count + 1);
            Part part = partRepository.findById(randomId).orElse(null);

            parts.add(part);
        }

        return parts;
    }
}
