package com.maygul.order.exception;

import com.maygul.order.exception.data.DataNotFoundException;
import com.maygul.order.exception.data.ExceptionTypeEnum;

public class OrderNotFoundException extends DataNotFoundException {
    public OrderNotFoundException() {
        super(ExceptionTypeEnum.ORDER_NOT_FOUND.getCode(),
                ExceptionTypeEnum.ORDER_NOT_FOUND.getInternalizationKey());
    }
}
