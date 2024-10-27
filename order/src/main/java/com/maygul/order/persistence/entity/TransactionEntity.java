package com.maygul.order.persistence.entity;

import com.maygul.order.persistence.converter.MapToStringConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEntity {
    @Id
    private String id;
    @Convert(converter = MapToStringConverter.class)
    private Map<Long, Integer> reservedProducts;
    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;
}
