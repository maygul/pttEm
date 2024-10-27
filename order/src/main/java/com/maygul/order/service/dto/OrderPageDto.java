package com.maygul.order.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPageDto {
    List<OrderDto> orders;
    Integer totalPages;
}
