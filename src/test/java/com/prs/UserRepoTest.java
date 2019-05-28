package com.prs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepoTest {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void findByUserNameShouldReturnUser() {
		entityManager.persist(new User("tuser","pwd","fn","lname","phone","email",true,true));
		Optional<User> user = this.userRepo.findByUserName("tuser");
		assertThat(user.get().getFirstName()).isEqualTo("fn");
	}
}
