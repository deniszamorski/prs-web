package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.prs.business.JsonResponse;
import com.prs.business.User;
import com.prs.db.UserRepository;

	@CrossOrigin
	@RestController
	@RequestMapping("/users")
	public class UserController {

		@Autowired
		private UserRepository userRepo;

		@GetMapping("/")
		public JsonResponse getAll() {
			JsonResponse jr = null;
			try {
				jr = JsonResponse.getInstance(userRepo.findAll());
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		@GetMapping("/{id}")
		public JsonResponse get(@PathVariable int id) {
			JsonResponse jr = null;
			try {
				Optional<User> p = userRepo.findById(id);
				if (p.isPresent())
					jr = JsonResponse.getInstance(p);
				else
					jr = JsonResponse.getInstance("No user found for id: " + id);
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		@PostMapping("/")
		public JsonResponse add(@RequestBody User p) {
			JsonResponse jr = null;
			// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
			try {
				jr = JsonResponse.getInstance(userRepo.save(p));
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
		@PutMapping("/")
		public JsonResponse update(@RequestBody User p) {
			JsonResponse jr = null;
			// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
			try {
				if (userRepo.existsById(p.getId())) {
					jr = JsonResponse.getInstance(userRepo.save(p));
				}
				else {
					jr = JsonResponse.getInstance("User id: " +p.getId()+"does not extist and you are"
							+"attempting to save it");
				}
			} catch (Exception e) {
				e.printStackTrace();
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		@DeleteMapping("/")
		public JsonResponse delete(@RequestBody User p) {
			JsonResponse jr = null;
			// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
			try {
				if (userRepo.existsById(p.getId())) {
					userRepo.delete(p);
					jr = JsonResponse.getInstance("User deleted");
				}
				else {
					jr = JsonResponse.getInstance("User id: " +p.getId()+"does not extist and you are"
							+"attempting to save it");
				}
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		@DeleteMapping("/{id}")
		public JsonResponse deleteId(@PathVariable int id) {
			JsonResponse jr = null;
			try {
				Optional<User> user = userRepo.findById(id);
				if (user.isPresent()) {
					userRepo.deleteById(id);
					jr = JsonResponse.getInstance(user);
				} else
					jr = JsonResponse.getInstance("Delete failed. No user for id: " + id);
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		@PostMapping("/authenticate")
		public JsonResponse logon(@RequestParam String userName, @RequestParam String password) {
			JsonResponse jr = null;
			try {
				Optional<User> p = userRepo.findByUserNameAndPassword(userName, password);
				if (p.isPresent())
					jr = JsonResponse.getInstance(p);
				else
					jr = JsonResponse.getInstance("No user found for this username and password: " + userName + password);
			} catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
		
	}
