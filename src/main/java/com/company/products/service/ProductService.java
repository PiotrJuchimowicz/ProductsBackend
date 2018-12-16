package com.company.products.service;

import com.company.products.dto.CategoryDto;
import com.company.products.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto create(ProductDto productDto);

    ProductDto read(Long id);

    List<ProductDto> readAll();

    List<ProductDto> readFromSummary();

    ProductDto update(ProductDto productDto);

    void delete(Long id);

    ProductDto addCategory(Long productId, CategoryDto categoryDto);
}
