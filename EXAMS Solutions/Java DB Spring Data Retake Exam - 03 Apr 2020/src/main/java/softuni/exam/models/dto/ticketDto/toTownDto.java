package softuni.exam.models.dto.ticketDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "to-town")
@XmlAccessorType(XmlAccessType.FIELD)
public class toTownDto {

    @XmlElement(name = "name")
    private String name;

    public toTownDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
