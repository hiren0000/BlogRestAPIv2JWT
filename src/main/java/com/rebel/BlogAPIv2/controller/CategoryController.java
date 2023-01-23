package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.CategoryDto;
import com.rebel.BlogAPIv2.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{coId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer coId)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, coId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory()
    {
        return new ResponseEntity<>(this.categoryService.getALlCategories(), HttpStatus.FOUND);
    }

    @GetMapping("/{coId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer coId)
    {
        CategoryDto category = this.categoryService.getCategoryById(coId);
        return new ResponseEntity<>(category, HttpStatus.FOUND);
    }

    @DeleteMapping("/{coId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer coId)
    {
        this.categoryService.deleteCategory(coId);
        return new ResponseEntity<>(new ApiResponse("Category is successfully deleted"+coId, true), HttpStatus.OK);
    }
}
