package com.prs.web;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;

@RestController
@RequestMapping("/purchase-requests")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository prRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(prRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> p = prRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No purchase request found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/submit-new")
	public JsonResponse submitNewPR(@RequestBody User u) {
		JsonResponse jr = null;
		PurchaseRequest p = new PurchaseRequest();
		p.setStatus("New");
		p.setDateNeeded(LocalDateTime.now());
		p.setUser(u);
		// NOTE: May need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			jr = JsonResponse.getInstance(prRepo.save(p));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PostMapping("/submit-review")
	public JsonResponse submitForReview(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		if (p.getTotal() <= 50) {
			p.setStatus("Approved");
		} else {
			p.setStatus("Review");
		}
		p.setDateNeeded(LocalDateTime.now());
		// NOTE: May need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			jr = JsonResponse.getInstance(prRepo.save(p));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		p.setStatus("New");
		// NOTE: May need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (prRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(prRepo.save(p));
			} else {
				jr = JsonResponse.getInstance(
						"Purchase request id: " + p.getId() + "does not extist and you are" + "attempting to save it");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/approve")
	public JsonResponse approve(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		p.setStatus("Approve");
		try {
			if (prRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(prRepo.save(p));
			} else {
				jr = JsonResponse.getInstance("Purchase request id: " + p.getId() + "does not extist and you are"
						+ "attempting to approve it");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/reject")
	public JsonResponse reject(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		p.setStatus("Reject");
		p.setReasonForRejection("");
		try {
			if (prRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(prRepo.save(p));
			} else {
				jr = JsonResponse.getInstance("Purchase request id: " + p.getId() + "does not extist and you are"
						+ "attempting to reject it");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequest p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type
		// needs to be caught
		try {
			if (prRepo.existsById(p.getId())) {
				prRepo.delete(p);
				jr = JsonResponse.getInstance("Purchase request deleted");
			} else {
				jr = JsonResponse.getInstance(
						"Pruchase request id: " + p.getId() + "does not extist and you are" + "attempting to save it");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/list-review/{id}")
	public JsonResponse listReview(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Iterable<PurchaseRequest> pr = prRepo.findAllByStatusAndUserIdNot("Review",id);
			if (pr!=null) 
				jr = JsonResponse.getInstance(pr);
				else 
				jr = JsonResponse.getInstance("No purchaseRequest found");
			}
			catch (Exception e) {
				jr = JsonResponse.getInstance(e);
			}
			return jr;
		}
	}


