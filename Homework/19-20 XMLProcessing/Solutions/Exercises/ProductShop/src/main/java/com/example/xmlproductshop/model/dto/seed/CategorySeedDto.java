package com.example.xmlproductshop.model.dto.seed;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CategorySeedDto {

    @Size(min = 3, max = 15)
    private String name;
}
