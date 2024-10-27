package com.maygul.product.api;

import com.maygul.product.api.request.ProductCrudRequest;
import com.maygul.product.service.ProductService;
import com.maygul.product.service.dto.ProductDto;
import com.maygul.product.service.dto.ProductPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<ProductPageDto> search(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(productService.getByPage(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductDto> save(@RequestBody ProductCrudRequest productDto) {
        return ResponseEntity.ok(productService.save(productDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id,
                                             @RequestBody ProductCrudRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ids")
    public ResponseEntity<List<ProductDto>> getByIds(@RequestParam String ids) {
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        return ResponseEntity.ok(productService.getByIds(idList));
    }

    @PostMapping("/reserve-products")
    public ResponseEntity reserveProducts(@RequestBody Map<Long, Integer> productsWithCounts) {
        productService.reserveProducts(productsWithCounts);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/put-reserved-products-back")
    public ResponseEntity putReservedProductsBack(@RequestBody Map<Long, Integer> negativeDiff) {
        productService.putReservedProductsBack(negativeDiff);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
