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
@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.NONE)
public class SuppliersWithCountOfPartsRootDto {

    @NonNull
    @XmlElement(name = "supplier")
    private List<SupplierWithCountOfPartsDto> suppliers;
}
