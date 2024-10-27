package com.maygul.product.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "ProductPageDto", description = "Object For Product Page Response. Used in search and list operations.")
public class ProductPageDto {
    @Schema(description = "Product List")
    List<ProductDto> products;
    @Schema(description = "Available Page Count")
    int totalPages;
}
