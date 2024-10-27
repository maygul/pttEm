package com.maygul.order.exception;

import com.maygul.order.exception.data.ExceptionTypeEnum;
import com.maygul.order.exception.data.ServiceCallException;

public class ProductServiceCallException extends ServiceCallException {
    public ProductServiceCallException() {
        super(ExceptionTypeEnum.PRODUCT_SERVICE_CALL_ERROR.getCode(),
                ExceptionTypeEnum.PRODUCT_SERVICE_CALL_ERROR.getInternalizationKey());
    }
}
