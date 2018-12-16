package com.company.products.controller;

import com.company.products.dto.CategoryDto;
import com.company.products.dto.ProductDto;
import com.company.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public void create(@RequestBody ProductDto productDto) {
        log.info("Received json: " + productDto);
        productService.create(productDto);
    }

    @PostMapping("/addCategory/{id}")
    public ProductDto addCategory(@RequestBody CategoryDto categoryDto,@PathVariable("id") Long id){
        log.info("Adding category : " + categoryDto + " for product with id: " + id);
        return productService.addCategory(id,categoryDto);
    }

    @GetMapping("/{id}")
    public ProductDto read(@PathVariable("id") Long id) {
        log.info("Getting product with id: " + id);
        return productService.read(id);
    }

    @GetMapping
    public List<ProductDto> readAll() {
        log.info("Getting all products");
        return productService.readAll();
    }


    @PutMapping
    public void update(@RequestBody ProductDto productDto) {
        log.info("Received product for updating: " + productDto);
        productService.update(productDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        log.info("Removing product with id: " + id);
        productService.delete(id);
    }
}
