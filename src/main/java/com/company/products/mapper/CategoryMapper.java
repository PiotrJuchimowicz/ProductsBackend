package com.company.products.mapper;

import com.company.products.dto.CategoryDto;
import com.company.products.model.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CategoryMapper {

    CategoryDto toDto(CategoryEntity categoryEntity);
}
