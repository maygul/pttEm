package com.maygul.order.external.product.service;

import com.maygul.order.external.product.service.dto.ProductDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {
    List<ProductDto> getAllWithIdList(Set<Long> longs);

    Boolean reserveProducts(Map<Long, Integer> productsWithCounts);

    Boolean putReservedProductsBack( Map<Long, Integer> negativeDiff);

}
