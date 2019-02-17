package com.facefy.facefy_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facefy.facefy_api.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
	
	Transaction[] findByCustomerId(String customerId);

}
