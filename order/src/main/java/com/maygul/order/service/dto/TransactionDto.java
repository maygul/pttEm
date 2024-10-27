package com.maygul.order.service.dto;

import com.maygul.order.persistence.entity.TransactionStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "TransactionDto", description = "Object For Transaction Information that includes products in order")
public class TransactionDto {
    @Schema(description = "Transaction Id")
    private String id;
    @Schema(description = "Reserved Products Id and Count Map")
    private Map<Long, Integer> reservedProducts;
    @Schema(description = "Transaction Status",examples = "CREATED, COMPLETED, CANCELLED")
    private TransactionStatusEnum status;
}
