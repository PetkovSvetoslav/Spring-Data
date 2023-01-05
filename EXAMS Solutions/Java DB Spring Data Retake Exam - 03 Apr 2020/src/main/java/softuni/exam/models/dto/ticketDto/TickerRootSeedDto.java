package softuni.exam.models.dto.ticketDto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "tickets")
@XmlAccessorType(XmlAccessType.FIELD)
public class TickerRootSeedDto {

    @XmlElement(name = "ticket")
    private List<TicketSeedDto> tickets;

    public TickerRootSeedDto() {
    }

    public List<TicketSeedDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketSeedDto> tickets) {
        this.tickets = tickets;
    }
}
