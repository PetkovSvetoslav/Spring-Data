package com.example.xmlcardealer.model.dto;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.NONE)
public class CarsWithIdRootDto {

    @NonNull
    @XmlElement(name = "car")
    private List<CarWithIdDto> cars;
}
