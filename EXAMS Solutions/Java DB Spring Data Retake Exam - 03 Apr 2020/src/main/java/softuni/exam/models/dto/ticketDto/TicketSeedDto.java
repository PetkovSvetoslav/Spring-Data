package softuni.exam.models.dto.ticketDto;

import softuni.exam.models.entity.Passenger;
import softuni.exam.models.entity.Plane;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;

    @XmlElement(name = "price")
    private BigDecimal price;

    @XmlElement(name = "take-off")
    private String takeoff;

    @XmlElement(name = "from-town")
    private fromTownDto fromTownDto ;

    @XmlElement(name = "to-town")
    private toTownDto toTownDto;

    @XmlElement(name = "plane")
    private TicketPlaneDto plane;

    @XmlElement(name = "passenger")
    private TicketPassengerDto passenger;

    public TicketSeedDto() {
    }

    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }





    public softuni.exam.models.dto.ticketDto.fromTownDto getFromTownDto() {
        return fromTownDto;
    }

    public void setFromTownDto(softuni.exam.models.dto.ticketDto.fromTownDto fromTownDto) {
        this.fromTownDto = fromTownDto;
    }

    public softuni.exam.models.dto.ticketDto.toTownDto getToTownDto() {
        return toTownDto;
    }

    public void setToTownDto(softuni.exam.models.dto.ticketDto.toTownDto toTownDto) {
        this.toTownDto = toTownDto;
    }

    public TicketPlaneDto getPlane() {
        return plane;
    }

    public void setPlane(TicketPlaneDto plane) {
        this.plane = plane;
    }

    public TicketPassengerDto getPassenger() {
        return passenger;
    }

    public void setPassenger(TicketPassengerDto passenger) {
        this.passenger = passenger;
    }
}
