package com.maygul.order.service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductDto {
    private Long productId;
    private Integer count;
}
