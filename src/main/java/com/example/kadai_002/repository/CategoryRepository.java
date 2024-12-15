package com.example.kadai_002.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.kadai_002.entity.Category;


public interface CategoryRepository  extends JpaRepository<Category, Integer> {	
	
	Page<Category> findByCategoryNameContaining(String keyword, Pageable pageable);
	public Category findByCategoryName(String categoryname); 
	
	
}