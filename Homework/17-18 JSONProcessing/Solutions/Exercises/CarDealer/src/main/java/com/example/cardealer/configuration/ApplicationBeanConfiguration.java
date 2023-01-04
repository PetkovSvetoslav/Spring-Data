package com.example.cardealer.configuration;

import com.example.cardealer.model.dto.CustomerWithSalesDto;
import com.example.cardealer.model.dto.seed.CustomerSeedDto;
import com.example.cardealer.model.entity.Customer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDateTime> localDateConverter = ctx -> ctx.getSource() == null ? null :
                LocalDateTime.parse(ctx.getSource());

        modelMapper.createTypeMap(CustomerSeedDto.class, Customer.class)
                .addMappings(mapper ->
                        mapper.using(localDateConverter)
                                .map(CustomerSeedDto::getBirthDate, Customer::setBirthDate));

        PropertyMap<Customer, CustomerWithSalesDto> customerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        };

        modelMapper.addMappings(customerMap);

        return modelMapper;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }
}
