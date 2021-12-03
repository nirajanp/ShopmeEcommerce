package com.shopme.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

	// this method will return List of Category object which does not have top level Category
	@Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
	public List<Category> findRootCategories();
	
}
