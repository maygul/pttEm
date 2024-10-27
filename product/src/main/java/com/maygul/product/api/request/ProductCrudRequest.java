package com.maygul.product.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ProductCrudRequest", description = "Object For Product Crud Request")
public class ProductCrudRequest {
    @Schema(description = "Product Name")
    private String name;
    @Schema(description = "Product Count")
    private Long stockCount;
}
