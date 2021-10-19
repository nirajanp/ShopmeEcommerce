package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;

@DataJpaTest // leverages DataJpaTest of Spring Data JPA

//@AutoConfigureTestDatabase is used to run unit test method against the real database 
// By default Spring Data JPA will run the test against in-memory database.
// If we want to test with real database then we need to override default configuration
// by using (replace = Replace.None)
@AutoConfigureTestDatabase (replace = Replace.NONE)

// Tells Spring Data JPA to commit the changes after running the test
@Rollback(false)
public class RoleRepositoryTest {
	
	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		Role roleAdmin = new Role("Admin", "manage everything");
		Role savedRole = repo.save(roleAdmin);
		
		// the role object has been persisted into the database
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	// By doing this we don't need to create database table manually in database. We just 
	// need to create entity class (i.e Role). Then run the Unit test and Hibernate 
	// will generate the table, create the table in the database.
	
	@Test
	public void testCreateRestRole() {
		Role roleSalesperson = new Role("Salesperson", "manage product price, customer,"
				+ "shipping, orders and sales report");
		
		Role roleEditor = new Role("Editor", "manage categories, brands"
				+ "products, articles and menus");
		
		Role roleShipper = new Role("Shipper", "view products, view orders, "
				+ "update order status");
		
		Role roleAssistant = new Role("Assistant", "manage questions and reviews");
		
		repo.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
		
		
	}
	
	

}
