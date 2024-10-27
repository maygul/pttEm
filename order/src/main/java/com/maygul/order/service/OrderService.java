package com.maygul.order.service;

import com.maygul.order.service.dto.OrderDto;
import com.maygul.order.service.dto.OrderProductDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(Long userId, List<OrderProductDto> products);

    OrderDto updateOrder(Long userId, Long orderId, List<OrderProductDto> products);

    OrderDto completeOrder(Long orderId);

    OrderDto cancelOrder(Long orderId);

}
