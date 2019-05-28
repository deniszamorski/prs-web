package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequest;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;


@RestController
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {

	@Autowired
	private PurchaseRequestLineItemRepository prliRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(prliRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> p = prliRepo.findById(id);
			if (p.isPresent())
				jr = JsonResponse.getInstance(p);
			else
				jr = JsonResponse.getInstance("No purchase request line item found for id: " + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			jr = JsonResponse.getInstance(prliRepo.save(p));
			recalculate(p.getPurchaseRequest());
			jr = JsonResponse.getInstance(prliRepo.save(p));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (prliRepo.existsById(p.getId())) {
				jr = JsonResponse.getInstance(prliRepo.save(p));
				recalculate(p.getPurchaseRequest());
				jr = JsonResponse.getInstance(prliRepo.save(p));
			}
			else {
				jr = JsonResponse.getInstance("Purchase request line item id: " +p.getId()+"does not extist and you are"
						+"attempting to save it");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequestLineItem p) {
		JsonResponse jr = null;
		// NOTE: May need to enhance exception handling if more than one exception type needs to be caught
		try {
			if (prliRepo.existsById(p.getId())) {
				prliRepo.delete(p);
				recalculate(p.getPurchaseRequest());
				jr = JsonResponse.getInstance(prliRepo.save(p));
				jr = JsonResponse.getInstance("Purchase request line item deleted");
			}
			else {
				jr = JsonResponse.getInstance("Pruchase request line item id: " +p.getId()+"does not extist and you are"
						+"attempting to save it");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	public void recalculate(PurchaseRequest pr) {
        Iterable<PurchaseRequestLineItem> prli = prliRepo.findByPurchaseRequest(pr);
        double prliTotal = 0.0;
        for (PurchaseRequestLineItem x: prli) {
            prliTotal+=(x.getProduct().getPrice()*x.getQuantity());
        }
        // add to pr total
        pr.setTotal(prliTotal);
        System.out.println(pr.getTotal());
        
    }
}