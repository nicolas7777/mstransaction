package com.microservicio.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.microservicio.app.document.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionDao extends ReactiveMongoRepository<Transaction,String> {
		
	@Query(value = "'date' : {'$gte' : ?0, '$lte' : ?1}")
	public Flux<Transaction> findByDateforFeesCharged(String startdate, String endingdate);
	
}



