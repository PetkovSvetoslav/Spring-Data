package com.example.xmlproductshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersWithProductsRootDto {

    @NonNull
    @XmlAttribute
    private int count;

    @NonNull
    @XmlElement(name = "user")
    private List<UserWithProductsDto> users;
}
