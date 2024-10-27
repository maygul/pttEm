package com.maygul.product.mapper;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.persistence.entity.ProductEntity;
import com.maygul.product.service.dto.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(ProductEntity productEntity);
    List<ProductDto> toDtoList(List<ProductEntity> productEntities);
    ProductEntity toEntity(ProductDto productDto);
    ProductEntity toEntity(ProductCrudRequest request);
}
