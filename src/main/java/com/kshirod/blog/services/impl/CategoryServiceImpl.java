package com.kshirod.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kshirod.blog.entities.Category;
import com.kshirod.blog.exceptions.ResourceNotFoundException;
import com.kshirod.blog.payloads.CategoryDto;
import com.kshirod.blog.repositories.CategoryRepo;
import com.kshirod.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category save = this.categoryRepo.save(category);
		return this.modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updateCategory = this.categoryRepo.save(cat);
		return this.modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		this.categoryRepo.delete(cat);	
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> list = categories.stream().map((cat)-> this.modelMapper
				.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return list;
	}

}
