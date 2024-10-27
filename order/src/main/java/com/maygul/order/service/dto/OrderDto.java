package com.maygul.order.service.dto;

import com.maygul.order.persistence.entity.OrderStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "OrderDto", description = "Object For Order Information")
public class OrderDto {
    @Schema(description = "Order Id")
    private Long id;
    @Schema(description = "User Id")
    private Long userId;
    @Schema(description = "Order Status",examples = "CREATED, COMPLETED, CANCELLED")
    private OrderStatusEnum status;
    @Schema(description = "Transaction Information which included products and total count")
    private TransactionDto transaction;
    @Schema(description = "Created Date")
    private LocalDateTime createdAt;
    @Schema(description = "Updated Date")
    private LocalDateTime updatedAt;
}
