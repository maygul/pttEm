package com.maygul.product.service.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPageDto {
    List<ProductDto> products;
    int totalPages;
}
