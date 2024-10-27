package com.maygul.order.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "OrderProductDto", description = "Object For Order Product Request")
public class OrderProductDto {
    @Schema(description = "Product Id")
    private Long productId;
    @Schema(description = "Product Count")
    private Integer count;
}
