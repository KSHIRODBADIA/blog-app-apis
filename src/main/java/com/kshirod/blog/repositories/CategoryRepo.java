package com.kshirod.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshirod.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
