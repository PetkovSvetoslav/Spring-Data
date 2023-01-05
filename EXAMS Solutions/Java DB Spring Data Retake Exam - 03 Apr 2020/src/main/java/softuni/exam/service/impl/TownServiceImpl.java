package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWNS_FILE_PATH = "src/main/resources/files/json/towns.json";

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper modelMapper
            , ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();
        TownSeedDto[] townSeedDtos = gson.fromJson(readTownsFileContent(),TownSeedDto[].class);

        Arrays.stream(townSeedDtos)
                .filter(dto -> {
                    boolean isValid = validationUtil.isValid(dto) && !checkTownByName(dto.getName());

                    sb.append(isValid ? String.format("Successfully imported Town %s - " +
                            "%d",
                            dto.getName(),
                            dto.getPopulation())
                            : "Invalid Town ")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(dto -> modelMapper.map(dto, Town.class))
                .forEach(townRepository::save);

        return sb.toString();
    }

    @Override
    public Boolean ifExists(String towName) {
        return this.townRepository.existsByName(towName);
    }

    @Override
    public Town getTownByName(String towName) {
        return this.townRepository.findByName(towName);
    }

    private boolean checkTownByName(String name) {
        return this.townRepository.existsByName(name);
    }
}
