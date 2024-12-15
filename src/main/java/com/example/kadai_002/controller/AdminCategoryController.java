package com.example.kadai_002.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.kadai_002.entity.Category;
import com.example.kadai_002.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
	 private final CategoryRepository categoryRepository;
	     
	     public AdminCategoryController(CategoryRepository categoryRepository) {
	         this.categoryRepository = categoryRepository;
	     }    
	     
	     @GetMapping
	     public String index(
	         @RequestParam(name = "keyword", required = false) String keyword,
	         @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
	         Model model) {

	         Page<Category> categoryPage;

	         if (keyword != null && !keyword.isEmpty()) {
	             // 部分一致検索
	             categoryPage = categoryRepository.findByCategoryNameContaining(keyword, pageable);
	         } else {
	             // 全件検索
	             categoryPage = categoryRepository.findAll(pageable);
	         }

	         model.addAttribute("categoryPage", categoryPage); // カテゴリーデータ
	         model.addAttribute("keyword", keyword); // 検索キーワード

	         return "admin/category/index"; // テンプレートファイル名
	     }
	     
	     
	     
	     @PostMapping("/{id}/delete")
	     public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
	    	 categoryRepository.deleteById(id);
	                 
	         redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");
	         
	         return "redirect:/admin/category";
	     }    
	 }