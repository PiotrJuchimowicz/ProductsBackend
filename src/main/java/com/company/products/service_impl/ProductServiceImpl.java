package com.company.products.service_impl;

import com.company.products.dto.CategoryDto;
import com.company.products.dto.ProductDto;
import com.company.products.exception.NotFoundException;
import com.company.products.mapper.ProductMapper;
import com.company.products.model.CategoryEntity;
import com.company.products.model.ProductEntity;
import com.company.products.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements com.company.products.service.ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final Logger log;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.log = LoggerFactory.getLogger(ProductServiceImpl.class);
    }


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
    public ProductDto create(ProductDto productDto) {
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
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if (productEntityOptional.isPresent()) {
            ProductEntity productEntity = productEntityOptional.get();
            log.info("Found entity: " + productEntity);
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setName(categoryDto.getName());
            productEntity.getCategories().add(categoryEntity);
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
}
