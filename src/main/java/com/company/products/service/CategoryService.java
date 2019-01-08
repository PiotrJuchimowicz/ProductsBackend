package com.company.products.service;

import com.company.products.dto.CategoryDto;
import com.company.products.model.CategoryEntity;

public interface CategoryService {
    CategoryDto findByName(String name);
}
