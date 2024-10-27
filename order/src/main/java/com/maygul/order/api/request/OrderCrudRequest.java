package com.maygul.order.api.request;

import com.maygul.order.service.dto.OrderProductDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCrudRequest {
    @NotNull
    private List<OrderProductDto> products;
    @NotNull
    private Long userId;
}
