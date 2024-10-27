package com.maygul.order.service;

import com.maygul.order.persistence.entity.TransactionEntity;

import java.util.Map;

public interface TransactionService {

    boolean checkIfProductsAvailable(Map<Long, Integer> productsWithCounts);

    TransactionEntity createTransaction(String transactionId, Map<Long, Integer> productsWithCounts);

    TransactionEntity updateTransaction(String transactionId, Map<Long, Integer> productsWithCounts);

    void cancelTransaction(String transactionId);

    void completeTransaction(String transactionId);
}
