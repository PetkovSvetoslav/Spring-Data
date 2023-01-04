package com.example.xmlcardealer.model.dto.seed;

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
@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.NONE)
public class PartsSeedRootDto {

    @NonNull
    @XmlElement(name = "part")
    private List<PartSeedDto> parts;
}
