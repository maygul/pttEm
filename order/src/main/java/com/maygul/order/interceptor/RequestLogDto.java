package com.maygul.order.interceptor;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLogDto {
    private LogTypeEnum type;
    private String url;
    private Map<String, Object> methodParams;
    private Map<String, String> headers;
    private Object requestBody;
    private String requestBodyClass;
    private String mediaType;
    private String method;
    private String clientIp;
}
