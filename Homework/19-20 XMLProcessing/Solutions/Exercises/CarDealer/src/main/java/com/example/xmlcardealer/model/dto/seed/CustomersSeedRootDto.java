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
@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.NONE)
public class CustomersSeedRootDto {

    @NonNull
    @XmlElement(name = "customer")
    private List<CustomerSeedDto> customers;
}
