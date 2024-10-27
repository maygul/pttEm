package com.maygul.order.exception;

import com.maygul.order.exception.data.ExceptionTypeEnum;
import com.maygul.order.exception.data.MicroException;

public class ProductOutOfStockException extends MicroException {
    public ProductOutOfStockException() {
        super(ExceptionTypeEnum.PRODUCT_OUT_OF_STOCK.getCode(),
                ExceptionTypeEnum.PRODUCT_OUT_OF_STOCK.getInternalizationKey());
    }
}
