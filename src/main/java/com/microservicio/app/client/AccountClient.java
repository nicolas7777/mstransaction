package com.microservicio.app.client;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.app.document.Transaction;
import com.microservicio.app.dto.AccountDto;
import com.microservicio.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AccountClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountClient.class);
	
	@Autowired
	@Qualifier("account")
	private WebClient webClient;

	public Mono<AccountDto> updateDeposit(Transaction p ){
		//LOGGER.info("BankingProductClient");
		AccountDto accountDto= new AccountDto();
		accountDto.setAccountcode(p.getAccountcode());
		accountDto.setAmount(p.getAmount());
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",p.getAccountcode());
		return webClient.put()
				.uri("/updatedeposit/{accountcode}",pathVariable)
				.body(BodyInserters.fromObject(accountDto))
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
		
	}

	public Mono<AccountDto> updateRetirement(Transaction p){
		//LOGGER.info("BankingProductClient");
		AccountDto accountDto= new AccountDto();
		accountDto.setAccountcode(p.getAccountcode());
		accountDto.setAmount(p.getAmount());
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",p.getAccountcode());
		return webClient.put()
				.uri("/updateretirement/{accountcode}",pathVariable)
				.body(BodyInserters.fromObject(accountDto))
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
				
	}
	public Mono<AccountDto> getNumbereDepositByAccountcode(String accountcode){
		//LOGGER.info("BankingProductClient");
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",accountcode);
		return webClient.get()
				.uri("/getnumberedepositbyaccountcode/{accountcode}",pathVariable)				
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
				
	}
	public Mono<AccountDto> getNumbereRetirementByAccountcode(String accountcode){
		//LOGGER.info("BankingProductClient");
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",accountcode);
		return webClient.get()
				.uri("/getNumbereRetirementbyaccountcode/{accountcode}",pathVariable)				
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
				
	}	
	
	public Flux<AccountDto> findAll(){
		//LOGGER.info("BankingProductClient");		
		return webClient.get()
				.uri("/findall")				
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToFlux(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
				
	}
	
	public Mono<AccountDto> findByAccountCode(String accountcode){
		//LOGGER.info("BankingProductClient");
		System.out.println(">>Account valores1"+ accountcode);
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",accountcode);
		return webClient.get()
				.uri("/findbyaccountcode/{accountcode}",pathVariable)				
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
				
	}
	public Mono<AccountDto> updateinterbanktransaction(Transaction p ){
		//LOGGER.info("BankingProductClient");
		AccountDto accountDto= new AccountDto();
		accountDto.setAccountcode(p.getAccountcode());
		accountDto.setAmount(p.getAmount());
		Map<String, String> pathVariable = new HashMap<String,String>();
		pathVariable.put("accountcode",p.getAccountcode());
		return webClient.put()
				.uri("/updateinterbanktransaction/{accountcode}",pathVariable)
				.body(BodyInserters.fromObject(accountDto))
				.retrieve()//Perform the HTTP request and retrieve the reponse body:
				.bodyToMono(AccountDto.class);//Extract the body to a Mono. By default, if the response has status code 4xx or 5xx,the Mono wil contain a WebClientException. This can be overriddenwith onStatus(Predicate, Function).
		
	}	
}
