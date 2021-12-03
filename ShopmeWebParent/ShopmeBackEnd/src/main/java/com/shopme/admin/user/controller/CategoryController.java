package com.shopme.admin.user.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.category.CategoryNotFoundException;
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/categories")
	public String listAll(Model model) {
		List<Category> categoryLists = categoryService.listAll();
		model.addAttribute("categoryList", categoryLists);
		return "categories/categories";
	}
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> listCategories = categoryService.listCategoriesUsedInForm();
		
		model.addAttribute("category", new Category());
		System.out.println(listCategories);
		model.addAttribute("listCategories", listCategories);
		model.addAttribute("pageTitle", "Create New Category");
		
		return "categories/category_form"; 
	}
	
	// input type for file upload is file so we use @RequestParam
	@PostMapping("/categories/save")
	public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes ra ) throws IOException {
		
		// We need to check if multipartFile is not empty we save the 
		// file uploaded else we just save the category.
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			category.setImage(fileName);
			
			// we call the method of category service method to persist the category object into DB
			Category savedCategory = categoryService.save(category);
			String uploadDir = "../category-images/" + savedCategory.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			categoryService.save(category);
		}
		String message = "The Category has been saved successfully.";
		ra.addFlashAttribute("message", message);
		
		return "redirect:/categories";
	}
	
	@GetMapping("/category/edit/{id}")
	public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Category category = categoryService.get(id);
			List<Category> listCategories = categoryService.listCategoriesUsedInForm();
			model.addAttribute("category", category);
			model.addAttribute("listCategories", listCategories);
			String pageTitle = "Edit Category (ID: " + id + ")";
			model.addAttribute("pageTitle", pageTitle);
			return "categories/category_form";
		} catch (CategoryNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		return "redirect:/categories";
	}
}
