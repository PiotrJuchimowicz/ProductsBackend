package com.company.products.controller;

import com.company.products.dto.CategoryDto;
import com.company.products.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(path = "/{name}")
    public CategoryDto getByName(@PathVariable("name") String name){
        log.info("Getting category by name: "+ name);
        return categoryService.findByName(name);
    }
}
