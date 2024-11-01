package com.maygul.order.service.impl;

import com.maygul.order.exception.OrderNotFoundException;
import com.maygul.order.exception.ProductOutOfStockException;
import com.maygul.order.external.product.service.ProductService;
import com.maygul.order.mapper.OrderMapper;
import com.maygul.order.persistence.entity.OrderEntity;
import com.maygul.order.persistence.entity.OrderStatusEnum;
import com.maygul.order.persistence.entity.TransactionStatusEnum;
import com.maygul.order.persistence.repository.OrderRepository;
import com.maygul.order.persistence.specification.OrderSpecification;
import com.maygul.order.service.OrderService;
import com.maygul.order.service.TransactionService;
import com.maygul.order.service.dto.OrderDto;
import com.maygul.order.service.dto.OrderPageDto;
import com.maygul.order.service.dto.OrderProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductService productService;
    private final TransactionService transactionService;

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, List<OrderProductDto> products) {
        var productMap = products.stream().collect(Collectors.toMap(OrderProductDto::getProductId, OrderProductDto::getCount));
        if (transactionService.checkIfProductsAvailable(productMap)) {
            UUID transactionId = UUID.randomUUID();
            var transaction = transactionService.createTransaction(transactionId.toString(), productMap);
            var orderEntity = OrderEntity.builder()
                    .userId(userId)
                    .transaction(transaction)
                    .build();
            orderRepository.save(orderEntity);
            return orderMapper.toDto(orderEntity);
        } else {
            throw new ProductOutOfStockException();
        }

    }

    @Override
    public OrderDto updateOrder(Long userId, Long orderId, List<OrderProductDto> products) {
        var orderEntity = getOrderEntityById(orderId);
        var transactionEntity = transactionService.updateTransaction(orderEntity.getTransaction().getId(), products.stream().collect(Collectors.toMap(OrderProductDto::getProductId, OrderProductDto::getCount)));
        orderEntity.setTransaction(transactionEntity);
        return orderMapper.toDto(orderEntity);
    }


    @Override
    public OrderDto completeOrder(Long orderId) {
        var orderEntity = getOrderEntityById(orderId);
        orderEntity.getTransaction().setStatus(TransactionStatusEnum.COMPLETED);
        orderEntity.setStatus(OrderStatusEnum.COMPLETED);
        orderRepository.save(orderEntity);
        return orderMapper.toDto(orderEntity);
    }

    @Override
    @Transactional
    public OrderDto cancelOrder(Long orderId) {
        var orderEntity = getOrderEntityById(orderId);
        var transactionEntity = orderEntity.getTransaction();
        transactionService.cancelTransaction(transactionEntity.getId());
        orderEntity.setStatus(OrderStatusEnum.CANCELLED);

        orderRepository.save(orderEntity);
        return orderMapper.toDto(orderEntity);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderMapper.toDto(getOrderEntityById(orderId));
    }

    @Override
    public OrderPageDto getOrderList(Long userId, Integer size, Integer page) {
        var orderSpec = OrderSpecification.hasUserId(userId);
        PageRequest pageRequest = PageRequest.of(page, size);
        var orderPage = orderRepository.findAll(orderSpec, pageRequest);

        var orderList = orderPage.getContent();
        var pageSize = orderPage.getTotalPages();
        return OrderPageDto.builder()
                .totalPages(pageSize)
                .orders(orderMapper.toOrderDtos(orderList))
                .build();
    }

    private OrderEntity getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException());
    }
}
