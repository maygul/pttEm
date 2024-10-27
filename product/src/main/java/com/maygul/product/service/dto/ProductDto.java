package com.maygul.product.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ProductDto", description = "Object For Product Response")
public class ProductDto {
    @Schema(description = "Product Id")
    private Long id;
    @Schema(description = "Product Name")
    private String name;
    @Schema(description = "Product Count")
    private Long stockCount;
}
