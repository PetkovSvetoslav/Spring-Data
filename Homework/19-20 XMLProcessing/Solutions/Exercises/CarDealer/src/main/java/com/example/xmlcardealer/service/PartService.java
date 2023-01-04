package com.example.xmlcardealer.service;

import com.example.xmlcardealer.model.dto.seed.PartSeedDto;
import com.example.xmlcardealer.model.entity.Part;

import java.util.Collection;
import java.util.Set;

public interface PartService {

    void seedParts(Collection<PartSeedDto> parts);

    Set<Part> getRandomParts(int lower, int upper);
}
