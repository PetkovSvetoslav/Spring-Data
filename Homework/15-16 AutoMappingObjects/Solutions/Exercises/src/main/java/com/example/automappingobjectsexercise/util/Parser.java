package com.example.automappingobjectsexercise.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    public static Map<String, String> parseMap(String[] values, String delimiter) {
        return Arrays.stream(values)
                .map(e -> e.split(delimiter))
                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
    }
}
