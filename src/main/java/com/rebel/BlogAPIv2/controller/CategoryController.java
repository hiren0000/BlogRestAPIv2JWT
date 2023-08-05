package com.rebel.BlogAPIv2.controller;

import com.rebel.BlogAPIv2.payloads.ApiResponse;
import com.rebel.BlogAPIv2.payloads.CategoryDto;
import com.rebel.BlogAPIv2.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category/")
@CrossOrigin("*")
public class CategoryController
{
    @Autowired
    private CategoryService categoryService;


    //Creating new category
    @PostMapping("/")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
        CategoryDto addedCategory = this.categoryService.saveCategory(categoryDto);

        HttpStatus status = HttpStatus.CREATED;
        String message = "Category has been crated successfully !! ";

        Map<String, Object> map = Map.of("category", addedCategory, "Status", status, "message", message);

        return ResponseEntity.ok(map);

    }

    //update category
    @PutMapping("/{coId}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer coId)
    {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, coId);

        HttpStatus status = HttpStatus.OK;
        String message = "Category has been updated successfully !! ";

        Map<String, Object> map = Map.of("category", updatedCategory, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //get all list of categories
    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize)
    {
        List<CategoryDto> list = this.categoryService.getALlCategories(pageNumber, pageSize);

        HttpStatus status = HttpStatus.OK;
        String message = "Categories are getting fetched from db !! ";

        Map<String, Object> map = Map.of("category", list, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //get category by id
    @GetMapping("/{coId}")
    public ResponseEntity<?> getById(@PathVariable Integer coId)
    {
        CategoryDto category = this.categoryService.getCategoryById(coId);

        HttpStatus status = HttpStatus.OK;
        String message = "Category is getting fetched from db !! ";

        Map<String, Object> map = Map.of("category", category, "Status", status, "message", message);

        return ResponseEntity.ok(map);
    }

    //Delete category by id
    @DeleteMapping("/{coId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer coId)
    {
        this.categoryService.deleteCategory(coId);

        HttpStatus status = HttpStatus.OK;
        String message = "Category has been deleted from db !! ";

        Map<String, Object> map = Map.of("Status", status, "message", message);

        return ResponseEntity.ok(map);
    }
}
