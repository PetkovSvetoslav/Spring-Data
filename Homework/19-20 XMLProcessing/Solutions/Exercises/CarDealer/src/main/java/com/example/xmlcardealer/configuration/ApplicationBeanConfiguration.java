package com.example.xmlcardealer.configuration;

import com.example.xmlcardealer.model.dto.seed.CustomerSeedDto;
import com.example.xmlcardealer.model.entity.Customer;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDateTime> localDateTimeConverter = ctx -> ctx.getSource() == null ? null :
                LocalDateTime.parse(ctx.getSource());

        modelMapper.typeMap(CustomerSeedDto.class, Customer.class)
                .addMappings(mapping ->
                        mapping.using(localDateTimeConverter)
                                .map(CustomerSeedDto::getBirthDate, Customer::setBirthDate));

        return modelMapper;
    }
}
