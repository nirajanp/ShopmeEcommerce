package com.shopme.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepoo;
	
	public List<Category> listAll() {
		return (List<Category>) categoryRepoo.findAll();
	}
	
	public Category save(Category category) {
		return categoryRepoo.save(category);
	}
	
	public List<Category> listCategoriesUsedInForm() {
		List<Category> categoriesUsedInForm = new ArrayList<>();
		Iterable<Category> categoriesInDb = categoryRepoo.findAll();
		
		for (Category category : categoriesInDb) {
			// if true then, it is top level root category
			if (category.getParent() == null) {
				categoriesUsedInForm.add(Category.copyIdAndName(category));
				
				Set<Category> children = category.getChildren();
				
				for (Category subCategory : children) {
					String name = "--"+subCategory.getName();
					categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
					listChildren(categoriesUsedInForm, subCategory, 1);
				}
			}
 		}
		
		return categoriesUsedInForm;
		
	}
	
	private void listChildren(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
		int newSubLevel = subLevel + 1;
		Set<Category> children = parent.getChildren(); //a b 
		
		for (Category subCategory : children) {
			String name ="";
			for (int i = 0; i < newSubLevel; i++) {
				name += "--";
			}
			name += subCategory.getName();
			categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
			
			listChildren(categoriesUsedInForm, subCategory, newSubLevel);
		}
	}
	
	
}
