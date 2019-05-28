package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.Product;
import com.prs.db.ProductRepository;

@RestController
@RequestMapping("/Products")
public class ProductController {

	@Autowired
	private ProductRepository productRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(productRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<Product> p = productRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No product found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody Product p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(productRepo.save(p));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody Product p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (productRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(productRepo.save(p));
			}
			else {
				jr = JsonResponse.getInstance("Product id: " +p.getId()+"does not extist and you are"
						+"attempting to save it");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody Product p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (productRepo.existsById(p.getId())) {
				productRepo.delete(p);
				jr = JsonResponse.getInstance("Product deleted");
			}
			else {
				jr = JsonResponse.getInstance("Product id: " +p.getId()+"does not extist and you are"
						+"attempting to save it");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
}
