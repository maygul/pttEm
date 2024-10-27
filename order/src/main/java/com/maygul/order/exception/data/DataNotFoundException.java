package com.maygul.order.exception.data;

public class DataNotFoundException extends MicroException {

    public DataNotFoundException(String code, String internalizationKey) {
        super(code, internalizationKey);
    }
}
