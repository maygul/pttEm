package com.maygul.product.api.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCrudRequest {
    private String name;
    private Long stockCount;
}
