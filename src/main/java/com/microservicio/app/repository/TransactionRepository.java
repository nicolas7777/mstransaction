package com.microservicio.app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservicio.app.document.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction,String> {
	
	//@Query(value = "{'idtransaction' : ?0}")
	public Mono<Transaction> findByIdtransaction(String idtransaction);
	
}



