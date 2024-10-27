package com.maygul.product.service;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.service.dto.ProductDto;
import com.maygul.product.service.dto.ProductPageDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    ProductDto getById(Long id);
    ProductDto save(ProductCrudRequest request);
    void delete(Long id);
    ProductPageDto getByPage(int page, int size);

    ProductDto update(Long id, ProductCrudRequest request);

    List<ProductDto> getAllWithIdList(Set<Long> values);

     void reserveProducts(Map<Long, Integer> productsWithCounts);

    void putReservedProductsBack(Map<Long, Integer> reservedProducts);

    List<ProductDto> getByIds(List<Long> ids);
}
