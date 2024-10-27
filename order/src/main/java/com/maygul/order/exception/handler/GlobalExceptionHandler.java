package com.maygul.order.exception.handler;

import com.maygul.order.exception.data.DataNotFoundException;
import com.maygul.order.exception.data.ExceptionData;
import com.maygul.order.exception.data.ExceptionTypeEnum;
import com.maygul.order.exception.data.MicroException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String GENERAL_EXCEPTION = "exception.general";

    private final MessageSource messageSource;

    @Value("${spring.application.name}")
    private String applicationName;

    @ExceptionHandler(value = MicroException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionData handleMicroException(MicroException ex, Locale locale) {
        var exceptionData = new ExceptionData(applicationName, ex.getCode(), ex.getInternalizationKey());
        return errorResponse(exceptionData, locale, ex);
    }

    @ExceptionHandler(value = DataNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionData handleDataNotFoundException(MicroException ex, Locale locale) {
        var exceptionData = new ExceptionData(applicationName, ex.getCode(), ex.getInternalizationKey());
        return errorResponse(exceptionData, locale, ex);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData handleException(Exception ex, Locale locale) {
        var exceptionData = new ExceptionData(applicationName,"500", GENERAL_EXCEPTION);
        return errorResponse(exceptionData, locale, ex);
    }

    @ExceptionHandler(value = UndeclaredThrowableException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionData handleUndeclaredException(Exception ex, Locale locale) {
        var exceptionData = new ExceptionData(applicationName,"500", GENERAL_EXCEPTION);
        return errorResponse(exceptionData, locale, ex);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionData handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        var locale = LocaleContextHolder.getLocale();
        var errors = ex.getFieldErrors();
        var errorList = new ArrayList<ExceptionData.ValidationErrorData>();
        for (FieldError error : errors) {
            String message;
            try {
                message = messageSource.getMessage(error.getDefaultMessage(), null, locale);
            } catch (Exception ex2) {
                message = messageSource.getMessage(GENERAL_EXCEPTION, null, locale);
            }
            errorList.add(new ExceptionData.ValidationErrorData(error.getField(), message));
        }
        var exceptionData =
                ExceptionData.builder()
                        .applicationName(applicationName)
                        .errorMessage(ExceptionTypeEnum.VALIDATION_ERROR.getInternalizationKey())
                        .errorCode(ExceptionTypeEnum.VALIDATION_ERROR.getCode())
                        .errors(errorList)
                        .build();
        return errorResponse(exceptionData, locale, ex);
    }

    private ExceptionData errorResponse(ExceptionData data, Locale locale, Exception exception) {
        log.error("Exception handler caught an error : {}", ExceptionUtils.getStackTrace(exception));

        String errorMessage;
        try {
            errorMessage = messageSource.getMessage(data.getErrorMessage(), null, locale);
        } catch (Exception ex) {
            errorMessage = messageSource.getMessage(GENERAL_EXCEPTION, null, locale);
        }
        data.setErrorMessage(errorMessage);

        log.error("Exception response: {}", data);
        return data;
    }

}
