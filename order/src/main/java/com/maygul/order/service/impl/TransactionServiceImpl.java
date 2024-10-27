package com.maygul.order.service.impl;

import com.maygul.order.external.product.service.ProductService;
import com.maygul.order.mapper.TransactionMapper;
import com.maygul.order.persistence.entity.TransactionEntity;
import com.maygul.order.persistence.entity.TransactionStatusEnum;
import com.maygul.order.persistence.repository.TransactionRepository;
import com.maygul.order.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ProductService productService;

    @Override
    public boolean checkIfProductsAvailable(Map<Long, Integer> productsWithCounts) {
        var resultMap = new HashMap<Long, Boolean>();
        var productList = productService.getAllWithIdList(productsWithCounts.keySet());
        var count = productList.stream()
                .filter(product -> {
                    var expectedCount = productsWithCounts.get(product.getId());
                    var result = product.getStockCount() > expectedCount;
                    resultMap.put(product.getId(), result);
                    return result;
                }).count();

        return productsWithCounts.size() == count;
    }

    @Override
    public TransactionEntity createTransaction(String transactionId, Map<Long, Integer> productsWithCounts) {
        productService.reserveProducts(productsWithCounts);
        var entity = new TransactionEntity(transactionId, productsWithCounts, TransactionStatusEnum.CREATED);
        return transactionRepository.save(entity);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public TransactionEntity updateTransaction(String transactionId, Map<Long, Integer> productsWithCounts) {
        var entity = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        //find the difference between the reserved products and the new products
        var diff = new HashMap<Long, Integer>();
        entity.getReservedProducts().forEach((key, value) -> {
            var newCount = productsWithCounts.get(key);
            if (newCount != null) {
                diff.put(key, newCount - value);
            } else {
                diff.put(key, -value);
            }
        });
        //if the difference is positive, reserve the difference
        var positiveDiff = new HashMap<Long, Integer>();
        var negativeDiff = new HashMap<Long, Integer>();
        diff.forEach((key, value) -> {
            if (value > 0) {
                positiveDiff.put(key, value);
            } else if (value < 0) {
                negativeDiff.put(key, -value);
            }
        });

        //check if a new item added into transaction
        var newItems = new HashMap<Long, Integer>();
        productsWithCounts.forEach((key, value) -> {
            if (!entity.getReservedProducts().containsKey(key)) {
                newItems.put(key, value);
            }
        });

        //if the difference is positive or new items added, reserve the products
        if (positiveDiff.size() > 0 || newItems.size() > 0) {
            var combinedMap = new HashMap<>(positiveDiff);
            combinedMap.putAll(newItems);
            if (checkIfProductsAvailable(combinedMap)) {
                productService.reserveProducts(combinedMap);
            } else {
                throw new RuntimeException("Not enough stock for the new products");
            }
        }

        //if the difference is negative, put the reserved products back
        if (negativeDiff.size() > 0) {
            productService.putReservedProductsBack(negativeDiff);
        }

        entity.setReservedProducts(productsWithCounts);
        transactionRepository.save(entity);
        return entity;
    }

    @Override
    @Transactional
    public void cancelTransaction(String transactionId) {
        var entity = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        productService.putReservedProductsBack(entity.getReservedProducts());
        entity.setStatus(TransactionStatusEnum.CANCELLED);
        transactionRepository.save(entity);
    }

    @Override
    public void completeTransaction(String transactionId) {
        var entity = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
        entity.setStatus(TransactionStatusEnum.COMPLETED);
        transactionRepository.save(entity);
    }

}
