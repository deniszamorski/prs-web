package com.prs;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Product;
import com.prs.business.Vendor;
import com.prs.db.ProductRepository;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductTests {

		@Autowired
		private ProductRepository productRepo;
		@Autowired
		private VendorRepository vendorRepo;
		
		@Test
		public void testProductFindAll() {
			// get all products
			Iterable<Product> products = productRepo.findAll();
			assertNotNull(products);
		}
		@Test
		public void testProductAdd() {
			// iterate vendor
			Iterable<Vendor> vendors = vendorRepo.findAll();
			Vendor v = vendors.iterator().next();
			Product p = new Product(v,"i7+","iPhone",999.99,"1","www.iphone.com");
			assertNotNull(productRepo.save(p));
			assertEquals("iPhone",p.getName());
			
		}
	}


