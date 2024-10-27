package com.maygul.order.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "OrderPageDto", description = "Object For Order Page Response. Used in search and list operations.")
public class OrderPageDto {
    @Schema(description = "Order List")
    List<OrderDto> orders;
    @Schema(description = "Available Page Count")
    Integer totalPages;
}
