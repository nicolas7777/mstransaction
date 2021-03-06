package com.microservicio.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.microservicio.app.document.Transaction;
import com.microservicio.app.dto.AccountDto;
import com.microservicio.app.dto.ClientDto;
import com.microservicio.app.dto.InterbanktransactionDto;
import com.microservicio.app.dto.PeriodDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
	public Mono<Transaction> updateByIdtransaction(String id,  Transaction transaction);
	public Mono<AccountDto> deleteByIdtransaction(String id);	
	public Mono<AccountDto> createdeposit (Transaction transaction);
	public Mono<AccountDto> createretirement (Transaction transaction);
	public Flux<Transaction> findAll ();
	public Mono<AccountDto> interbanktransaction(InterbanktransactionDto interbanktransactionDto);
	public Mono<Transaction> create(Transaction transaction);
	public Flux<Transaction> findByDateforFeesCharged (PeriodDto periodDto);
	
}
 