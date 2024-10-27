package com.maygul.product.service.impl;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.mapper.ProductMapper;
import com.maygul.product.mapper.ProductMapperImpl;
import com.maygul.product.persistence.entity.ProductEntity;
import com.maygul.product.persistence.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceJUnitTest {

    static ProductServiceImpl productService;

    static ProductMapper productMapper;

    static ProductRepository productRepository;

    @BeforeAll
     static void setUp() {
        productMapper = new ProductMapperImpl();
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository,productMapper);
    }

    @Test
    public void testGetById() {
        //given
        var id = 1L;
        var product = new ProductEntity(id, "Product1", 100L);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        //when
        var result = productService.getById(id);
        //then
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void testGetByIdException() {
        //given
        var id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        //when/then
        assertThrows(RuntimeException.class, () -> productService.getById(id));
    }

    @Test
    public void testSave() {
        //given
        var request = new ProductCrudRequest("Product1", 100L);
        var product = new ProductEntity(1L, "Product1", 100L);
        when(productRepository.save(any())).thenReturn(product);
        //when
        var result = productService.save(request);
        //then
        assertEquals(product.getId(), result.getId());
    }

    @Test
    public void testUpdate() {
        //given
        var id = 1L;
        var request = new ProductCrudRequest("Product1", 100L);
        var product = new ProductEntity(id, "Product1 Updated", 100L);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        //when
        var result = productService.update(id, request);
        //then
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getStockCount(), result.getStockCount());
        verify(productRepository,times(1)).save(product);
    }



}
