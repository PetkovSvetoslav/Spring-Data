package com.example.xmlproductshop.config;

import com.example.xmlproductshop.model.dto.ProductWithSellerDto;
import com.example.xmlproductshop.model.dto.UserWithSoldProductsDto;
import com.example.xmlproductshop.model.entity.Product;
import com.example.xmlproductshop.model.entity.User;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<User, String> userStringConverter = new AbstractConverter<>() {
            @Override
            protected String convert(User user) {
                if (user == null) {
                    return null;
                } else {
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();

                    if (firstName == null || firstName.isBlank()) {
                        return lastName;
                    } else {
                        return firstName + " " + lastName;
                    }
                }
            }
        };

        modelMapper
                .typeMap(Product.class, ProductWithSellerDto.class)
                .addMappings(mapper ->
                        mapper
                                .using(userStringConverter)
                                .map(Product::getSeller, ProductWithSellerDto::setSellerNames)
                );

        modelMapper
                .typeMap(User.class, UserWithSoldProductsDto.class)
                .addMapping(User::getProducts, UserWithSoldProductsDto::setSoldProducts);

        return modelMapper;
    }
}
