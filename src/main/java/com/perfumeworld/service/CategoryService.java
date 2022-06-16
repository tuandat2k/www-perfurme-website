package com.perfumeworld.service;

import java.util.List;

import com.perfumeworld.dto.CategoryDto;



public interface CategoryService {
    List<CategoryDto> viewAllService();

    CategoryDto saveCategoryService(CategoryDto categoryDto);

    String deleteCategoryService(Integer id);

    Object[] viewPageCategoryService(int offset, int limit);

    CategoryDto findByIdCategoryService(Integer id);

    Object[] findFilterCategoryService(String[] filter);
}
