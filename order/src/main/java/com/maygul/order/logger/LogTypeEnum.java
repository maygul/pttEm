package com.maygul.order.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum LogTypeEnum {
    REQUEST_OUT, RESPONSE_IN, //restTemplate
    REQUEST_IN, RESPONSE_OUT; //controller

    private static final ObjectMapper mapper = new ObjectMapper();


    @PostConstruct
    private void init() {
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // to prevent adding escape characters to the string attributes in logged object
        mapper.getFactory().setCharacterEscapes(new JacksonCustomEscapeConfig());
    }


    public static String serialize(Object dto) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        } catch (Exception e) {
            log.error("Error while serializing object", e);
            return null;
        }
    }
}
