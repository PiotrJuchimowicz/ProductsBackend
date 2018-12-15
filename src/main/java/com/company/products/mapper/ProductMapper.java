package com.company.products.mapper;

import com.company.products.dto.ProductDto;
import com.company.products.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProductMapper {


    ProductDto productEntityToProductDto(ProductEntity productEntity);

    ProductEntity productDtoToProductEntity(ProductDto productDto);

    List<ProductDto> productEntitiesToProductDtos(List<ProductEntity> productEntities);

    @Mapping(target = "categories",ignore = true)
    void updateEntityFromDto(ProductDto productDto, @MappingTarget ProductEntity productEntity);

}
