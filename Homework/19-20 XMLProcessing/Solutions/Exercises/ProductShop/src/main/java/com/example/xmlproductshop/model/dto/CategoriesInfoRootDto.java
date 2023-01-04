package com.example.xmlproductshop.model.dto;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.NONE)
public class CategoriesInfoRootDto {

    @NonNull
    @XmlElement(name = "category")
    private List<CategoryInfoDto> categoriesInfo;
}
