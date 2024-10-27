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
    @NotNull(message = "validation.not.null")
    private List<OrderProductDto> products;
    @NotNull(message = "validation.not.null")
    private Long userId;
}
