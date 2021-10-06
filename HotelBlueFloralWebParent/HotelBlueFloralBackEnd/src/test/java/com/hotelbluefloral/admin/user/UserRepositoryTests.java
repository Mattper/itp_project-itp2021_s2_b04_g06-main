package com.hotelbluefloral.admin.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useRepresentation;

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

import com.hotelbluefloral.comman.entity.Role;
import com.hotelbluefloral.comman.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateUser() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userName = new User("arachchigepramod@gmail.com", "pramod348", "Pramod", "Arachchige");
		userName.addRole(roleAdmin);
		
		User savedUser = repo.save(userName);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
		
	}
	
	@Test
	public void testCreateUserWithTwoRoles() {
		User userName1 = new User("dilhanisilva@gmail.com", "dilhani348", "Dilhani", "Silva");
		Role roleEditor = new Role(3);
		Role roleShipper = new Role(4);
		
		userName1.addRole(roleEditor);
		userName1.addRole(roleShipper);
		
		User savedUser = repo.save(userName1);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User userNam =  repo.findById(1).get();
		System.out.println(userNam);
		assertThat(userNam).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userNam = repo.findById(1).get();
		userNam.setEnabled(true);
		userNam.setEmail("arachchigepramod348@gmail.com");
		
		repo.save(userNam);
	}
	
	@Test
	public void testUpdateUserRoles() {
		User userName1 = repo.findById(2).get();
		Role roleShipper = new Role(4);
		Role roleSalesperson = new Role(2);
		
		userName1.getRoles().remove(roleShipper);
		userName1.addRole(roleSalesperson);
		
		repo.save(userName1);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
		
	}
	
	@Test
	public void testGetUserByEmail() {
		String email = "ruhunu@gmail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountById() {
		Integer id = 12;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 5;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 6;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 0;
		int pageSize = 4;
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		List<User> listUsers = page.getContent();
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
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
