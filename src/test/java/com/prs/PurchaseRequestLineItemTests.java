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
import com.prs.business.PurchaseRequestLineItem;
import com.prs.business.Product;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.ProductRepository;
import com.prs.db.PurchaseRequestLineItemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PurchaseRequestLineItemTests {

		@Autowired
		private PurchaseRequestLineItemRepository prliRepo;
		
		@Autowired
		private PurchaseRequestRepository prRepo;
		
		@Autowired
		private ProductRepository pRepo;
		
		@Test
		public void testPurchaseRequestLineItemFindAll() {
			// get all vendors
			Iterable<PurchaseRequestLineItem> prli = prliRepo.findAll();
			assertNotNull(prli);
		}
		@Before
		public void testPurchaseRequestLineItemAdd() {
			// iterate purchase request
			Iterable<PurchaseRequest> prs = prRepo.findAll();
			PurchaseRequest pr = prs.iterator().next();
			Iterable<Product> products = pRepo.findAll();
			Product p = products.iterator().next();
			PurchaseRequestLineItem prli = new PurchaseRequestLineItem(pr,p,2);
			assertNotNull(prliRepo.findAll());
			assertEquals(2,prli.getQuantity());
			
			
		}
	}


