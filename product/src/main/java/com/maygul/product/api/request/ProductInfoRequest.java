package com.maygul.product.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ProductInfoRequest", description = "Object For Product Info Request")
public class ProductInfoRequest {
    @Schema(description = "Product Ids")
    @NotNull
    private List<Long> ids;
}
