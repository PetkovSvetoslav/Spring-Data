package com.example.xmlproductshop.model.dto.seed;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDto {

    @Size(min = 3)
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;
}
