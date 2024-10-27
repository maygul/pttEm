package com.maygul.product.api;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.api.request.ProductInfoRequest;
import com.maygul.product.api.request.ReserveProductRequest;
import com.maygul.product.service.ProductService;
import com.maygul.product.service.dto.ProductDto;
import com.maygul.product.service.dto.ProductPageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Search products",
            description = "Search products",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = ProductPageDto.class)))
            }
    )
    @GetMapping("/")
    public ResponseEntity<ProductPageDto> search(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(productService.getByPage(page, size));
    }

    @Operation(summary = "Get Product By Id",
            description = "Get Product By Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = ProductDto.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @Operation(summary = "Save Product",
            description = "Save Product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = ProductDto.class)))
            }
    )
    @PostMapping("/")
    public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductCrudRequest productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @Operation(summary = "Update Product",
            description = "Update Product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = ProductDto.class)))
            }
    )
    @PostMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable(name = "id") Long id,
                                             @RequestBody @Valid ProductCrudRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @Operation(summary = "Delete Product",
            description = "Delete Product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Provide product info for order",
            description = "Provide product info for order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = ProductDto[].class)))
            }
    )
    @GetMapping("/provide-product-info-for-order")
    public ResponseEntity<List<ProductDto>> provideProductInfoForOrder(@RequestBody @Valid ProductInfoRequest request) {
        var idList = request.getIds();
        return ResponseEntity.ok(productService.provideProductInfoForOrder(idList));
    }

    @Operation(summary = "Reserve products",
            description = "Reserve products will be used for order in order service. Product stock will be decreased",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PostMapping("/reserve-products")
    public ResponseEntity reserveProducts(@RequestBody @Valid ReserveProductRequest request) {
        Map<Long, Integer> productsWithCounts = request.getProductAndCountMap();
        productService.reserveProducts(productsWithCounts);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(summary = "Put reserved products back",
            description = "Put reserved products back to stock. This operation will be used when order is cancelled",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK")
            }
    )
    @PostMapping("/put-reserved-products-back")
    public ResponseEntity putReservedProductsBack(@RequestBody @Valid ReserveProductRequest request) {
        Map<Long, Integer> negativeDiff = request.getProductAndCountMap();
        productService.putReservedProductsBack(negativeDiff);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
