package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.CategoryDto;
import com.rebel.BlogAPIv2.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category/")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto addedCategory = this.categoryService.saveCategory(categoryDto);
        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);

    }

}
