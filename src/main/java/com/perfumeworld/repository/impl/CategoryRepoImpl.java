package com.perfumeworld.repository.impl;

import org.springframework.stereotype.Repository;

import com.perfumeworld.model.Category;
import com.perfumeworld.repository.CategoryRepo;


@Repository
public class CategoryRepoImpl extends AbstractRepoImpl<Integer, Category> implements CategoryRepo {

}
