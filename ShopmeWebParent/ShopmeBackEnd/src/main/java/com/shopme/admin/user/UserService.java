package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service // this is business class so we use Service annotation of Spring FW
@Transactional // because we used UPDATE in UserRepository
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static final int USERS_PER_PAGE = 4;
	
	// this methods returns list of users
	public List<User> listAll(){
		return (List<User>) userRepo.findAll();
	}
	
	// returns small set of user objects for specific page numbers
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		System.out.println("PageNum: " +  pageNum);
		
		Pageable pageable = PageRequest.of(pageNum - 1,USERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return userRepo.findAll(keyword, pageable);
		}
		return userRepo.findAll(pageable);
	}
	
	public List<Role> listRoles() {
		return (List<Role>) roleRepo.findAll();	
	}

	public User save(User user) {
		boolean isUpdatingUser = (user.getId() !=null);
		
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			// encode password before it saves to the database
			encodePassword(user);
		}

		// saves user to the database
		return userRepo.save(user);
	}
	
	// this method will get user's password and encode it.
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	// this will take the email entered by user in the form page, 
	// and check that in the repository. 
	public boolean isEmailUnique(Integer id, String email) {
		
		User userByEmail = userRepo.getUserByEmail(email);
		
		// if there is no user in db (i.e. null) then email is unique.
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
	}

	// this method returns a whole user object. 
	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}
	
	// here countById is used because we don't need the whole user object
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id);
		
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		userRepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userRepo.updateEnabledStatus(id, enabled);
	}
}
