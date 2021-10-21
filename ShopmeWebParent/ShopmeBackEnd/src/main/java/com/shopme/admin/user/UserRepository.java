package com.shopme.admin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shopme.common.entity.User;


public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
	// We use @Query annotation here to specify custom JPA query
	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email) ;
	
	// this method follows the convention specified by Spring Data JPA,
	// so we don't have to specify any SQL statement.
	public Long countById(Integer id);
	
	// method to implement search functionality
	// search based on firstName and lastName column below commented query
	// %?1% is a placeholder for value of first parameter which is keyword 
	//	@Query("SELECT u FROM User u WHERE u.firstName LIKE %?1% OR u.lastName LIKE %?1%"
	//			+ "OR u.email LIKE %?1%")
	
	// search functionality that uses concatenated value.
	@Query("SELECT u FROM User u WHERE CONCAT (u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	
	// Update User u SET u.enabled property to value of second parameter of the method
	// where u.id is first parameter of the method
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying // because we are using UPDATE statement we need to use this @Modifying 
	// annotation here.
	public void updateEnabledStatus(Integer id, boolean enabled);

}
