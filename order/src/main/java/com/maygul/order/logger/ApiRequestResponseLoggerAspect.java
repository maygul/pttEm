package com.maygul.order.logger;

import com.maygul.order.utils.StaticUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class ApiRequestResponseLoggerAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    protected void restController() {}

    @Pointcut("execution(public * com.maygul..*.*(..))")
    protected void apiOperation() {}

    @Before("restController() && apiOperation()")
    public void logBefore(JoinPoint jointPoint) {
        logRequest(jointPoint);
    }

    @AfterReturning(pointcut = "restController() && apiOperation()", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {
        logResponse((ResponseEntity) response);
    }

    private void logRequest(JoinPoint joinPoint) {
        RequestLogDto requestLogDto = null;

        var signature = (MethodSignature) joinPoint.getSignature();
        var joinPointArgs = joinPoint.getArgs();
        var parameters = signature.getMethod().getParameters();

        Map<String, Object> parameterMap = new HashMap<>();
        String requestBodyClass = null;
        Object requestBody = null;
        for (int i = 0; i < parameters.length; i++) {
            var parameter = parameters[i];
            var requestBodyAnnotation =
                    Arrays.stream(parameter.getDeclaredAnnotations())
                            .filter(annotation -> annotation instanceof RequestBody)
                            .findFirst();
            if (requestBodyAnnotation.isPresent()) {
                requestBody = joinPointArgs[i];
                requestBodyClass = parameter.getType().getSimpleName();
            } else {
                parameterMap.put(parameter.getName(), joinPointArgs[i]);
            }
        }

        var servletRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        var uri = servletRequest.getRequestURI();
        var headers =
                Collections.list(servletRequest.getHeaderNames()).stream()
                        .collect(Collectors.toMap(h -> h, servletRequest::getHeader));
        var clientIp = StaticUtil.getClientIp(servletRequest);

        requestLogDto =
                RequestLogDto.builder()
                        .clientIp(clientIp)
                        .methodParams(parameterMap)
                        .headers(headers)
                        .requestBody(requestBody)
                        .requestBodyClass(requestBodyClass)
                        .method(servletRequest.getMethod())
                        .mediaType(headers.get("content-type"))
                        .url(uri)
                        .type(LogTypeEnum.REQUEST_IN)
                        .build();
        log.info("Request : {}", requestLogDto);
    }

    private void logResponse(ResponseEntity<?> response) {
        ResponseLogDto responseLogDto;

        final Optional<?> body = Optional.ofNullable(response.getBody());
        var responseBodyClass = body.<Class<?>>map(Object::getClass).orElse(null);
        var responseBody = body.isPresent() ? body.get() : null;
        int httpStatus = response.getStatusCode().value();
        var mediaType = response.getHeaders().getContentType();


        responseLogDto =
                ResponseLogDto.builder()
                        .headers(response.getHeaders().toSingleValueMap())
                        .responseBody(responseBody)
                        .responseBodyClass(
                                responseBodyClass != null ? responseBodyClass.getSimpleName() : "void")
                        .httpStatus(httpStatus)
                        .mediaType(mediaType)
                        .type(LogTypeEnum.RESPONSE_OUT)
                        .build();
        log.info("Response : {}", responseLogDto);
    }

}
