package com.maygul.order.exception;

import com.maygul.order.exception.data.DataNotFoundException;
import com.maygul.order.exception.data.ExceptionTypeEnum;

public class TransactionNotFoundException extends DataNotFoundException {

    public TransactionNotFoundException() {
        super(ExceptionTypeEnum.TRANSACTION_NOT_FOUND.getCode(),
                ExceptionTypeEnum.TRANSACTION_NOT_FOUND.getInternalizationKey());
    }
}
