package com.maygul.product.persistence.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.Map;

public class MapToStringConverter implements AttributeConverter<Map<Integer,Integer>,String> {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(Map<Integer, Integer> attribute) {
        if(attribute == null)
            return null;
        try{
            return mapper.writeValueAsString(attribute);
        }catch (IOException e){
            throw new RuntimeException("Error while converting map to string");
        }

    }

    @Override
    public Map<Integer, Integer> convertToEntityAttribute(String dbData) {
        if(dbData == null){
            return Map.of();
        }
        try {
            return mapper.readValue(dbData, new TypeReference<>() {
            });
        }catch (IOException e){
            throw new RuntimeException("Error while converting string to map");
        }
    }
}
