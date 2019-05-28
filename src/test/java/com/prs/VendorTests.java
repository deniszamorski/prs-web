package com.prs;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VendorTests {

		@Autowired
		private VendorRepository vendorRepo;
		
		@Test
		public void testVendorFindAll() {
			// get all vendors
			Iterable<Vendor> vendors = vendorRepo.findAll();
			assertNotNull(vendors);
		}
		@Before
		public void testVendorAdd() {
			// add vendor
			Vendor v = new Vendor("cd","name","address","city","OH","zip","phone","email",true);
			assertNotNull(vendorRepo.save(v));
			assertEquals("name",v.getName());
			
		}
	}


