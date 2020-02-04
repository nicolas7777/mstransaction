package com.microservicio.app.controller;

import java.net.URI;
import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.app.document.Transaction;
import com.microservicio.app.dto.AccountDto;
import com.microservicio.app.dto.ClientDto;
import com.microservicio.app.dto.InterbanktransactionDto;
import com.microservicio.app.service.ITransactionService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/transaction")
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	//inyeccion de dependencias
	@Autowired
	private ITransactionService transactionService;
	

	@PostMapping("/createdeposit")
	@ApiOperation(value = "CREATE DEPOSIT *", notes="")
	Mono<AccountDto> createdeposit(@RequestBody Transaction transaction){
		//LOGGER.info("TransactionController");		
		return this.transactionService.createdeposit(transaction);
	}
	
	@PostMapping("/createretirement")
	@ApiOperation(value = "CREATE RETIREMENT", notes="")
	Mono<AccountDto> createretirement(@RequestBody Transaction transaction){
		//LOGGER.info("TransactionController");		
		return this.transactionService.createretirement(transaction);
	}


	@DeleteMapping("/deletebyidtransaction/{idtransaction}")
	@ApiOperation(value = "CRUD", notes="")	
	Mono<AccountDto>deleteByIdtransaction(@PathVariable String idtransaction){
		//LOGGER.info("TransactionController");
		return this.transactionService.deleteByIdtransaction(idtransaction);
	}
	
	@PutMapping("/updatebyaccountcode/{accountcode}")
	@ApiOperation(value = "CRUD", notes="")	
	Mono<Transaction> updateByAccountCode(@PathVariable String idtransaction ,@RequestBody Transaction transaction){
		//LOGGER.info("TransactionController");
		return this.transactionService.updateByIdtransaction(idtransaction,transaction);
	}
	
	@GetMapping("/findall")
	@ApiOperation(value = "findAll", notes="")	
	Flux<Transaction> findall(){
		//LOGGER.info("TransactionController");
		return this.transactionService.findAll();
	}
	
	@PostMapping("/interbanktransaction")
	@ApiOperation(value = "interbank transaction", notes="")	
	Mono<AccountDto> interbanktransaction(@RequestBody InterbanktransactionDto interbanktransactionDto){
		//LOGGER.info("TransactionController");		
		return this.transactionService.interbanktransaction(interbanktransactionDto);
	}
	@PostMapping("/create")
	@ApiOperation(value = "CRUD", notes="It is only for create.")	
	Mono<Transaction> create(@RequestBody Transaction transaction){
		//LOGGER.info("TransactionController");		
		return this.transactionService.create(transaction);
	}	
	
}
