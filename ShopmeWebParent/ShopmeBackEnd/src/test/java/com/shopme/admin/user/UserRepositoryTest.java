package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest(showSql = false) // leverages DataJpaTest of Spring Data JPA

//@AutoConfigureTestDatabase is used to run unit test method against the real database 
//By default Spring Data JPA will run the test against in-memory database.
//If we want to test with real database then we need to override default configuration
//by using (replace = Replace.None)
@AutoConfigureTestDatabase(replace = Replace.NONE)

//Tells Spring Data JPA to commit the changes after running the test
@Rollback(false)
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager; // this class is provided by Spring Data JPA for unit testing repository
	
	@Test
	public void testCreateNewUserWithOneRole() {
		// this will find the specific role we want to assign to specific user.
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNirajanP = new User("nirajanp@code.edu", "nirp2021", "Nirajan", "Pandey");
		// add role to the user
		userNirajanP.addRole(roleAdmin);
		
		// save the user. this method will be implemented by Spring Data JPA at runtime and it returns a 
		// persisted object.
		User savedUser = repo.save(userNirajanP);
		
		// meaning that the object savedUser is persisted.
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testCreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
		
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		
		// add role to the user
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		// now we persist the user into repository
		User savedUser = repo.save(userRavi);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	// retrieve all users from the database
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		// forEach user of listUsers it will perform print operation
		listUsers.forEach(user -> System.out.println(user));
	}
	
	// test method to retrieve users based on id
	@Test
	public void testGetUsersById() {
		User userName = repo.findById(1).get();
		System.out.println(userName);
		assertThat(userName).isNotNull();
	}
	
	// test method to update the details of existing users
	@Test
	public void testUpdateUserDetails() {
		User userName = repo.findById(1).get();
		userName.setEnabled(true);
		userName.setEmail("nirajan@developer.com");
		
		repo.save(userName);
	}
	
	// test method to update role of the user
	@Test
	public void testUpdateUserRoles() {
		User userRavi = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesperson = new Role(2);
		
		userRavi.getRoles().remove(roleEditor);
		userRavi.addRole(roleSalesperson);
		
		repo.save(userRavi);
	}
	
	// test method to delete user
	@Test
	public void testDeleteUser() {
		Integer userId = 3;
		repo.deleteById(userId);
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "ravi@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);	
	}
	
	@Test
	public void testDisableUser() {
		Integer id = 1;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 7;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstpage() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers =  page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "bruce";
		
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers =  page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	

}
