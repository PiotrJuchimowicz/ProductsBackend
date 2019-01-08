package com.company.products.service_impl;

import com.company.products.dto.CategoryDto;
import com.company.products.mapper.CategoryMapper;
import com.company.products.model.CategoryEntity;
import com.company.products.repository.CategoryRepository;
import com.company.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto findByName(String name) {
        CategoryEntity entity = categoryRepository.findByName(name);
        log.info("Found category: " + entity);
        return categoryMapper.toDto(entity);
    }
}
