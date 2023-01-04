package com.example.xmlproductshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryInfoDto {

    @XmlAttribute
    private String name;

    @XmlElement(name = "product-count")
    private int productsCount;

    @XmlElement(name = "average-price")
    private BigDecimal averagePrice;

    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;
}
