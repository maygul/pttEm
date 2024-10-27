package com.maygul.order.api;

import com.maygul.order.api.request.OrderCrudRequest;
import com.maygul.order.service.OrderService;
import com.maygul.order.service.dto.OrderDto;
import com.maygul.order.service.dto.OrderPageDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderCrudRequest request) {
        var result = orderService.createOrder(request.getUserId(), request.getProducts());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update/{orderId}")

    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderCrudRequest request) {
        var result = orderService.updateOrder(request.getUserId(), orderId,request.getProducts());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete/{orderId}")
    public ResponseEntity<OrderDto> completeOrder(@PathVariable Long orderId) {
        var result = orderService.completeOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable Long orderId) {
        var result = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        var result = orderService.getOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/")
    public ResponseEntity<OrderPageDto> getOrderList(@RequestParam Long userId,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "0") Integer page) {
        var result = orderService.getOrderList(userId,size,page);
        return ResponseEntity.ok(result);
    }

}
