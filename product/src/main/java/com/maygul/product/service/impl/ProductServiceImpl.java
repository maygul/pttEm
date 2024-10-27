package com.maygul.product.service.impl;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.mapper.ProductMapper;
import com.maygul.product.persistence.repository.ProductRepository;
import com.maygul.product.service.ProductService;
import com.maygul.product.service.dto.ProductDto;
import com.maygul.product.service.dto.ProductPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDto getById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return mapper.toDto(product);
    }

    @Transactional
    @Override
    public ProductDto save(ProductCrudRequest request) {
        var product = mapper.toEntity(request);
        product = productRepository.save(product);
        return mapper.toDto(product);
    }


    @Transactional
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductPageDto getByPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        var products = productRepository.findAll(pageRequest);
        return ProductPageDto.builder()
                .products(mapper.toDtoList(products.getContent()))
                .totalPages(products.getTotalPages())
                .build();
    }

    @Transactional
    @Override
    public ProductDto update(Long id, ProductCrudRequest request) {
        var product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(request.getName());
        product.setStockCount(request.getStockCount());
        product = productRepository.save(product);
        return mapper.toDto(product);
    }

    @Override
    public List<ProductDto> getAllWithIdList(Set<Long> values) {
        var products = productRepository.findAllById(values);
        return mapper.toDtoList(products);
    }

    @Transactional
    @Override
    public void reserveProducts(Map<Long, Integer> productsWithCounts) {
        Collection<ProductDto> products = getAllWithIdList(productsWithCounts.keySet());
        products.forEach(productDto -> {
            if (productDto.getStockCount() < productsWithCounts.get(productDto.getId())) {
                throw new RuntimeException("Not enough stock for product with id: " + productDto.getId());
            }
        });
        products.forEach(productDto -> {
            productDto.setStockCount(productDto.getStockCount() - productsWithCounts.get(productDto.getId()));
            productRepository.save(mapper.toEntity(productDto));
        });
    }

    @Transactional
    @Override
    public void putReservedProductsBack(Map<Long, Integer> reservedProducts) {
        Collection<ProductDto> products = getAllWithIdList(reservedProducts.keySet());
        products.forEach(productDto -> {
            productDto.setStockCount(productDto.getStockCount() + reservedProducts.get(productDto.getId()));
            productRepository.save(mapper.toEntity(productDto));
        });
    }

    @Override
    public List<ProductDto> provideProductInfoForOrder(List<Long> ids) {
        var products = productRepository.findAllById(ids);
        return mapper.toDtoList(products);
    }
}
