package com.kshirod.blog.services;

import java.util.List;

import com.kshirod.blog.payloads.CategoryDto;

public interface CategoryService {
	//this interface name can be also CategoryService
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	
	//get one
	CategoryDto getCategory(Integer categoryId);
	
	//getAll
	List<CategoryDto> getAllCategory();
}
