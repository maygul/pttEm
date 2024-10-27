package com.maygul.order.service.dto;

import com.maygul.order.persistence.entity.OrderStatusEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatusEnum status;
    private TransactionDto transaction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
