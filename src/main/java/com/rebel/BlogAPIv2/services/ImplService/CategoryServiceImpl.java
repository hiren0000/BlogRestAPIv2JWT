package com.rebel.BlogAPIv2.services.ImplService;

import com.rebel.BlogAPIv2.enitities.Category;
import com.rebel.BlogAPIv2.exceptions.ResourceNotFoundException;
import com.rebel.BlogAPIv2.payloads.CategoryDto;
import com.rebel.BlogAPIv2.repo.CategoryRepo;
import com.rebel.BlogAPIv2.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService
{
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    //Adding Category
    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {

        Category category = this.mapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(category);

        return this.mapper.map(savedCategory, CategoryDto.class);
    }

    //Get all the categories
    @Override
    public List<CategoryDto> getALlCategories(Integer pageNumber, Integer pageSize)
    {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> page = this.categoryRepo.findAll(pageable);
        List<Category> categories =page.getContent();

           List<CategoryDto> dtos = categories.stream()
                      .map(category -> (this.mapper.map(category, CategoryDto.class))).collect(Collectors.toList());
        return dtos;
    }

    //Updating Category
    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer coId) {

        Category category = this.categoryRepo.findById(coId)
                            .orElseThrow(()-> new ResourceNotFoundException("Category", "coId", coId));

        category.setCoName(categoryDto.getCoName());
        category.setCoDes(categoryDto.getCoDes());

        Category updateCategory =this.categoryRepo.save(category);

        return this.mapper.map(updateCategory, CategoryDto.class);
    }

    //Get category by Id
    @Override
    public CategoryDto getCategoryById(Integer coId) {

        Category category = this.categoryRepo
                    .findById(coId).orElseThrow(() -> new ResourceNotFoundException("Category", "coId", coId));

        return this.mapper.map(category, CategoryDto.class);
    }

    //Delete Category
    @Override
    public void deleteCategory(Integer coId)
    {
        Category category = this.categoryRepo
                .findById(coId).orElseThrow(() -> new ResourceNotFoundException("Category", "coId", coId));

        this.categoryRepo.delete(category);

    }
}
