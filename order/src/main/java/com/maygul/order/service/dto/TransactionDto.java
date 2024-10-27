package com.maygul.order.service.dto;

import com.maygul.order.persistence.entity.TransactionStatusEnum;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private String transactionId;
    private Map<Long, Integer> reservedProducts;
    private TransactionStatusEnum status;
}
