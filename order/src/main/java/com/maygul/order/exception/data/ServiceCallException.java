package com.maygul.order.exception.data;

public class ServiceCallException extends MicroException {
    public ServiceCallException(String code, String internalizationKey) {
        super(code, internalizationKey);
    }
}
