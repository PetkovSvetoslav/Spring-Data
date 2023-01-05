package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerSeedDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {

    private static final String PASSENGER_FILE_PATH = "src/main/resources/files/json/passengers.json";

    private final PassengerRepository passengerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownService townService;

    public PassengerServiceImpl(PassengerRepository passengerRepository, Gson gson
            , ModelMapper modelMapper, ValidationUtil validationUtil, TownService townService) {
        this.passengerRepository = passengerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PASSENGER_FILE_PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        PassengerSeedDto [] passengerSeedDtos = gson.fromJson(readPassengersFileContent(),PassengerSeedDto[].class);
        StringBuilder sb = new StringBuilder();
        Arrays.stream(passengerSeedDtos)
                .filter(dto -> {
                    boolean isValid = validationUtil.isValid(dto) && this.townService.ifExists(dto.getTown());

                    sb.append(isValid ? String.format("Successfully imported Passenger %s - " +
                                            "%s",
                                    dto.getLastName(),
                                    dto.getEmail())
                                    : "Invalid Passenger")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(dto -> {
                    Passenger passenger = modelMapper.map(dto,Passenger.class);
                    Town town = this.townService.getTownByName(dto.getTown());

                    passenger.setTown(town);

                    return passenger;
                })
                .forEach(passengerRepository::save);

        return sb.toString();


    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() throws IOException {
        StringBuilder sb = new StringBuilder();

        this.passengerRepository.getAllPassengersOrderByTicketsCountDescThenByEmail()
                .forEach(p -> sb.append(String.format("Passenger %s %s\n" +
                        "\tEmail - %s\n" +
                        "Phone - %s\n" +
                        "\tNumber of tickets - %d",
                        p.getFirstName(),
                        p.getLastName(),
                        p.getEmail(),
                        p.getPhoneNumber(),
                        p.getTickets().size()))
                        .append(System.lineSeparator()));

        return sb.toString();
    }

    @Override
    public boolean checkIfPassengerExistByMail(String mail) {
        return this.passengerRepository.existsByEmail(mail);
    }

    @Override
    public Passenger getPassengerByEmail(String email) {
        return this.passengerRepository.getPassengerByEmail(email);
    }
}
