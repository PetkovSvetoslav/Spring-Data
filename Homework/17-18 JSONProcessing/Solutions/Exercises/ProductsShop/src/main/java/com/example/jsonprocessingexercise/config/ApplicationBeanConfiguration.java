package com.example.jsonprocessingexercise.config;

import com.example.jsonprocessingexercise.model.dto.UserWithSoldProductsDto;
import com.example.jsonprocessingexercise.model.entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(User.class, UserWithSoldProductsDto.class)
                .addMapping(User::getProducts, UserWithSoldProductsDto::setSoldProducts);

        return mapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
}
