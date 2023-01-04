package com.example.nextleveltechnologies.configuration;

import com.example.nextleveltechnologies.model.dto.seed.ProjectSeedDto;
import com.example.nextleveltechnologies.model.entity.Project;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<String, LocalDate> localDateConverter = ctx -> ctx == null ? null :
                LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        modelMapper.addConverter(localDateConverter);

        modelMapper
                .typeMap(ProjectSeedDto.class, Project.class)
                .addMappings(m -> m.using(localDateConverter).map(ProjectSeedDto::getStartDate, Project::setStartDate));

        return modelMapper;
    }
}
