package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ticketDto.TickerRootSeedDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Plane;
import softuni.exam.models.entity.Ticket;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TicketRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.service.PlaneService;
import softuni.exam.service.TicketService;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String TICKETS_FILE_PATH = "src/main/resources/files/xml/tickets.xml";

    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final TownService townService;
    private final PassengerService passengerService;
    private final PlaneService planeService;


    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper,
                             ValidationUtil validationUtil, XmlParser xmlParser, TownService townService
            , PassengerService passengerService, PlaneService planeService) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
        this.passengerService = passengerService;
        this.planeService = planeService;
    }


    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(TICKETS_FILE_PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        TickerRootSeedDto tickerRootSeedDto = xmlParser.fromFile(TICKETS_FILE_PATH,TickerRootSeedDto.class);
        StringBuilder sb = new StringBuilder();

        tickerRootSeedDto.getTickets()
                .stream()
                .filter(dto -> {
                    boolean isValid = validationUtil.isValid(dto)
                            && checkIfTownExist(dto.getFromTownDto().getName())
                            && checkIfTownExist(dto.getToTownDto().getName())
                            && checkIfPassengerExist(dto.getPassenger().getEmail())
                            && checkIfPlaneExist(dto.getPlane().getRegisterNumber());

                    sb.append(isValid ? String.format("Successfully imported Ticket %s - " +
                                            "%s",
                                    dto.getFromTownDto().getName(),
                                    dto.getToTownDto().getName())
                                    : "Invalid Ticket")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(dto -> {
                    Ticket ticket = modelMapper.map(dto,Ticket.class);
                    Town fromTown = this.townService.getTownByName(dto.getFromTownDto().getName());
                    Town toTown = this.townService.getTownByName(dto.getToTownDto().getName());
                    Passenger passenger = this.passengerService.getPassengerByEmail(dto.getPassenger().getEmail());
                    Plane plane = this.planeService.getPlaneByRegisterNumber(dto.getPlane().getRegisterNumber());

                    ticket.setFromTown(fromTown);
                    ticket.setToTown(toTown);
                    ticket.setPassenger(passenger);
                    ticket.setPlane(plane);
                    ticket.setTakeoff(LocalDateTime.parse(dto.getTakeoff()
                            ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                    return ticket;
                })
                .forEach(ticketRepository::save);

        return sb.toString();
    }

    private boolean checkIfPlaneExist(String plane) {
        return this.planeService.existRegisterNumber(plane);
    }

    private boolean checkIfPassengerExist(String email) {
        return this.passengerService.checkIfPassengerExistByMail(email);
    }

    private boolean checkIfTownExist(String townName) {
        return this.townService.ifExists(townName);
    }
}
