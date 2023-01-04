package com.example.automappingobjectsexercise.configuration;

import com.example.automappingobjectsexercise.model.dto.game.GameDto;
import com.example.automappingobjectsexercise.model.entity.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<String, LocalDate> localDateConverter =
                ctx -> ctx.getSource() == null
                        ? null
                        : LocalDate.parse(ctx.getSource(),
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")
                );

        mapper.addConverter(localDateConverter);

        mapper
                .typeMap(GameDto.class, Game.class)
                .addMapping(GameDto::getThumbnailURL, Game::setImageThumbnail)
                .addMappings(m -> m.using(localDateConverter).map(GameDto::getReleaseDate, Game::setReleaseDate));

        return mapper;
    }

    @Bean
    public BufferedReader reader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
