package com.example.xmlproductshop.model.dto.seed;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSeedDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @NotNull
    @Size(min = 3)
    @XmlAttribute(name = "last-name", required = true)
    private String lastName;

    @XmlAttribute
    private byte age;
}
