package com.maygul.product.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World from products service";
    }
}
