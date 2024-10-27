package com.maygul.order.exception.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionData {

    @Schema(description = "Application name that the exception is thrown from")
    private String applicationName;

    @Schema(description = "Error code of the exception")
    private String errorCode;

    @Schema(description = "Error message of the exception")
    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationErrorData> errors;

    @Schema(description = "Validation error data. It contains request field validation errors.")
    public record ValidationErrorData(String field, String message) {
    }

    public ExceptionData(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ExceptionData(String errorCode, String errorMessage, List<ValidationErrorData> errors) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public ExceptionData(String applicationName,String errorCode, String errorMessage) {
        this.applicationName = applicationName;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
