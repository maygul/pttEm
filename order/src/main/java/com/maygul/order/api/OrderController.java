package com.maygul.order.api;

import com.maygul.order.api.request.OrderCrudRequest;
import com.maygul.order.exception.data.ExceptionData;
import com.maygul.order.service.OrderService;
import com.maygul.order.service.dto.OrderDto;
import com.maygul.order.service.dto.OrderPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Create Order",
            description = "Create Order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Product out of stock",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderCrudRequest request) {
        var result = orderService.createOrder(request.getUserId(), request.getProducts());
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Update Order",
            description = "Update Order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Product out of stock",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @PostMapping("/update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderCrudRequest request) {
        var result = orderService.updateOrder(request.getUserId(), orderId,request.getProducts());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Complete Order",
            description = "Complete Order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @PostMapping("/complete/{orderId}")
    public ResponseEntity<OrderDto> completeOrder(@PathVariable Long orderId) {
        var result = orderService.completeOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Cancel Order",
            description = "Cancel Order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable Long orderId) {
        var result = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get Order",
            description = "Get Order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        var result = orderService.getOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get Order List",
            description = "Get Order List for specific User",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = OrderPageDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Order not found",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal error during processing",
                            content = @Content(schema = @Schema(implementation = ExceptionData.class)))
            }
    )
    @GetMapping("/")
    public ResponseEntity<OrderPageDto> getOrderList(@RequestParam Long userId,
                                                     @RequestParam(defaultValue = "10") Integer size,
                                                     @RequestParam(defaultValue = "0") Integer page) {
        var result = orderService.getOrderList(userId,size,page);
        return ResponseEntity.ok(result);
    }

}
