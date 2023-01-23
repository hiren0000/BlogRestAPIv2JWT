package com.rebel.BlogAPIv2.services;

import com.rebel.BlogAPIv2.payloads.CategoryDto;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

//Service to perform all the required performance

public interface CategoryService
{
    //creating category
    public CategoryDto saveCategory(CategoryDto categoryDto);

    //getting all the categories
    public List<CategoryDto> getALlCategories();

    //update category
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer coId);

    //get category by id
    public CategoryDto getCategoryById(Integer coId);

    //delete category by id
    public void deleteCategory(Integer coId);
}
