package com.example.xmlproductshop.model.dto;

import lombok.Data;
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
@Data
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.NONE)
public class UsersWithSoldProductsRootDto {

    @NonNull
    @XmlElement(name = "user")
    List<UserWithSoldProductsDto> users;
}
