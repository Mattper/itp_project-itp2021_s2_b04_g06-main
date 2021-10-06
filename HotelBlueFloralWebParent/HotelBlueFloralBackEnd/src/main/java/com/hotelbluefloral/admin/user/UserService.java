package com.hotelbluefloral.admin.user;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbluefloral.comman.entity.Role;
import com.hotelbluefloral.comman.entity.User;

@Service
@Transactional
public class UserService {
	
	public static final int USERS_PER_PAGE = 4;
	
	@Autowired
	private UserRepository userrepo;
	
	@Autowired
	private RoleRepository rolerepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User getByEmail(String email) {
		return userrepo.getUserByEmail(email);
	}
	
	public List<User> listAll(){
		return (List<User>) userrepo.findAll();
	}
	
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);
		if (keyword != null) {
			return userrepo.findAll(keyword, pageable);
		}
		return userrepo.findAll(pageable);
	}
	
	public List<Role> listRoles(){
		return (List<Role>) rolerepo.findAll();
	}

	public User save(User user) {
		boolean isUpdatingUser = (user.getId() != null);
		if (isUpdatingUser) {
			User existingUser = userrepo.findById(user.getId()).get();
			if (user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		
		return userrepo.save(user);
		
	}
	
	public User updateAccount(User userInForm) {
		User userInDB = userrepo.findById(userInForm.getId()).get();
		
		if (!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		if (userInForm.getPhoto() != null) {
			userInDB.setPhoto(userInForm.getPhoto());
		}
		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		
		return userrepo.save(userInDB);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userrepo.getUserByEmail(email);
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

	public User get(Integer id) throws UserNotFoundException {
	  try {
		return userrepo.findById(id).get();
	  } catch (NoSuchElementException ex) {
		  throw new UserNotFoundException("Could not find any user with ID" + id);
	  }
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userrepo.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID" + id);
		}
		userrepo.deleteById(id);
	}
	
	public void updateUserEnabledStatus(Integer id, boolean enabled) {
		userrepo.updateEnabledStatus(id, enabled);
	}

}
