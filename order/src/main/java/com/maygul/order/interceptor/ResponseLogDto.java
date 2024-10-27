package com.maygul.order.interceptor;

import lombok.*;
import org.springframework.http.MediaType;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLogDto {
    private LogTypeEnum type;
    private Map<String, String> headers;
    private String responseBodyClass;
    private Object responseBody;
    private int httpStatus;
    private MediaType mediaType;
}
