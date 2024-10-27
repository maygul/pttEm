package com.maygul.order.exception.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.tools.Diagnostic;

@Getter
@AllArgsConstructor
public enum ExceptionTypeEnum {
    INTERNAL_ERROR("2", "exception.internal.error"),
    VALIDATION_ERROR("1", "exception.validation.error"),
    ORDER_NOT_FOUND("-1", "exception.order.not.found"),
    TRANSACTION_NOT_FOUND("-2", "exception.transaction.not.found"),
    PRODUCT_SERVICE_CALL_ERROR("-3", "exception.product.service.call.error"),
    PRODUCT_OUT_OF_STOCK("-4", "exception.product.out.of.stock"),
    ORDER_UPDATE_PRODUCT_OUT_OF_STOCK("-5", "exception.order.update.product.out.of.stock");

    private String code;
    private String internalizationKey;
}
