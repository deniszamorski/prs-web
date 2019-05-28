package com.prs;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserTests {

		@Autowired
		private UserRepository userRepo;
		
		@Test
		public void testUserFindAll() {
			// get all users
			Iterable<User> users = userRepo.findAll();
			assertNotNull(users);
		}
		@Before
		public void testUserAdd() {
			User u = new User("uname","password","firstName","lastName","phoneNumber","email",true,true);
			assertNotNull(userRepo.save(u));
			assertEquals("lastName",u.getLastName());
			
		}
}
