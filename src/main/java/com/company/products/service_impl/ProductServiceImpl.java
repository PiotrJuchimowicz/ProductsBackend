package com.company.products.service_impl;

import com.company.products.dto.CategoryDto;
import com.company.products.dto.ProductDto;
import com.company.products.exception.NotFoundException;
import com.company.products.mapper.ProductMapper;
import com.company.products.model.CategoryEntity;
import com.company.products.model.ProductEntity;
import com.company.products.repository.CategoryRepository;
import com.company.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements com.company.products.service.ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDto read(Long id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            log.info("Found entity: " + productEntity);
            ProductDto productDto = productMapper.productEntityToProductDto(productEntity);
            log.info("Mapped entity to dto: " + productDto);
            return productDto;
        } else {
            throw new NotFoundException("Product with id: " + id + " did not found");
        }
    }

    @Override
    public List<ProductDto> readAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        log.info("Found product entities: " + productEntities);
        List<ProductDto> productDtos = productMapper.productEntitiesToProductDtos(productEntities);
        log.info("Returning product dtos: " + productDtos);
        return productDtos;
    }

    @Override
    public List<ProductDto> readFromSummary() {
        List<ProductEntity> productEntities = productRepository.findByIsInSummaryTrue();
        return productMapper.productEntitiesToProductDtos(productEntities);
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        List<CategoryDto> preparedCategories = this.prepareCategories(productDto.getCategories());
        productDto.setCategories(preparedCategories);
        ProductEntity productEntity = productMapper.productDtoToProductEntity(productDto);
        log.info("Mapped to entity: " + productEntity);
        productEntity = productRepository.save(productEntity);
        log.info("Saved entity: " + productEntity);
        productDto = productMapper.productEntityToProductDto(productEntity);
        log.info("Returning dto: " + productDto);
        return productDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productDto.getId());
        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            productMapper.updateEntityFromDto(productDto, productEntity);
            log.info("Updated entity: " + productEntity);
            productDto = productMapper.productEntityToProductDto(productEntity);
            return productDto;
        } else {
            throw new NotFoundException("Product with id: " + productDto.getId() + " did not found");
        }
    }

    public ProductDto addCategory(Long productId, CategoryDto categoryDto) {
        String preparedCategoryName = categoryDto.getName().trim().toUpperCase();
        categoryDto.setName(preparedCategoryName);
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            log.info("Found entity: " + productEntity);
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(categoryDto.getId());
            categoryEntity.setName(categoryDto.getName());
            if(!productEntity.getCategories().contains(categoryEntity)){
                productEntity.getCategories().add(categoryEntity);
            }
            productRepository.save(productEntity);
            return productMapper.productEntityToProductDto(productEntity);
        } else {
            throw new NotFoundException("Product with id: " + productId + " did not found");
        }
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    private List<CategoryDto> prepareCategories(List<CategoryDto> categories){
        List<CategoryDto> preparedCategories = new LinkedList<>();
        for(CategoryDto categoryDto : categories){
            String preparedCategoryName = categoryDto.getName().trim().toUpperCase();
            preparedCategories.add(new CategoryDto(categoryDto.getId(),preparedCategoryName));
        }
        return preparedCategories;
    }
}
