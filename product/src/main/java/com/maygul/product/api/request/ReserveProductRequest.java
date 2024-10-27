package com.maygul.product.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ReserveProductRequest", description = "Object For Reserve Product Request")
public class ReserveProductRequest {
    @Schema(description = "Product Id and Count Map")
    @NotNull
    private Map<Long, Integer> productAndCountMap;
}
