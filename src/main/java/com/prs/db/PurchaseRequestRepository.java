package com.prs.db;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.prs.business.PurchaseRequest;

public interface PurchaseRequestRepository extends CrudRepository<PurchaseRequest, Integer> {

	Optional<PurchaseRequest> findByStatus(String pr);

	Iterable<PurchaseRequest> findAllByStatusAndUserIdNot(String status, int id);

}
