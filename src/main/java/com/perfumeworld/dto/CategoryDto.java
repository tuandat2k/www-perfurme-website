package com.perfumeworld.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.perfumeworld.model.Category;



public class CategoryDto extends AbtractDto<CategoryDto> {
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(min = 1, message = "Tên danh mục không được để trống")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category convertToEntities() {
        Category entity = new Category();
        entity.setId(getId());
        entity.setName(getName());
        return entity;
    }
}
