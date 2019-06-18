package com.prs;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestTests {

		@Autowired
		private PurchaseRequestRepository prRepo;
		
		@Autowired
		private UserRepository userRepo;
		@Test
		public void testPurchaseRequestFindAll() {
			// get all vendors
			Iterable<PurchaseRequest> prs = prRepo.findAll();
			assertNotNull(prs);
		}
		@Before
		public void testPurchaseRequestAdd() {
			// iterate user
			Iterable<User> users = userRepo.findAll();
			User u = users.iterator().next();
			PurchaseRequest pr = new PurchaseRequest();
			assertNotNull(prRepo.save(pr));
			assertEquals("test",pr.getDescription());
		}
	}


