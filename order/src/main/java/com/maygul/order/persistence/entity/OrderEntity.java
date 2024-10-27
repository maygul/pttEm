package com.maygul.order.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderEntity {
    @Id
    private Long id;
    private Long userId;
    private OrderStatusEnum status;
    @OneToOne
    @JoinColumn(name = "transaction_id")
    private TransactionEntity transaction;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatusEnum.CREATED;
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
