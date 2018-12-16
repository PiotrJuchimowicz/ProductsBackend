package com.company.products.dto;


import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
    private List<CategoryDto> categories = new LinkedList<>();
}
