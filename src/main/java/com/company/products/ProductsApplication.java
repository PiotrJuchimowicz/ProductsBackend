package com.company.products;

import com.company.products.dto.ProductDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsApplication {
    public static void main(String[] args) {
        ProductDto productDto = new ProductDto();
        SpringApplication.run(ProductsApplication.class, args);
    }
}
