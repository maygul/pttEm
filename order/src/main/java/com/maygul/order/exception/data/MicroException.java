package com.maygul.order.exception.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MicroException extends RuntimeException{
    private String code;
    private String internalizationKey;
    private Object[] params;
    private String applicationName;

    public MicroException(String code, String internalizationKey) {
        this.code = code;
        this.internalizationKey = internalizationKey;
    }

    public MicroException(String code, String internalizationKey, Object[] params) {
        this.code = code;
        this.internalizationKey = internalizationKey;
        this.params = params;
    }
}
