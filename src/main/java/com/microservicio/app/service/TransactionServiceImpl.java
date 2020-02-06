package com.microservicio.app.service;

import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservicio.app.config.AccountConfig;
import com.microservicio.app.config.ClientConfig;
import com.microservicio.app.dao.TransactionDao;
import com.microservicio.app.document.Transaction;
import com.microservicio.app.dto.AccountDto;
import com.microservicio.app.dto.ClientDto;
import com.microservicio.app.dto.InterbanktransactionDto;
import com.microservicio.app.dto.PeriodDto;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements ITransactionService{
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);
	
	@Autowired
	private AccountConfig accountConfig;

	
	@Autowired
	private TransactionDao transactionDao;

	@Override
	public Mono<Transaction> updateByIdtransaction(String id,  Transaction transaction) {
		//LOGGER.info("TransactionServiceImpl");		
			return this.transactionDao
					.findById(id)
					.map(p->
						new Transaction (
								//transaction.getBankname(),
//								transaction.getKindaccount(),
								transaction.getId(),
								transaction.getAccountcode(),		
								transaction.getAmount(),
								transaction.getStatus(),
								new Date(),
//								transaction.getPercent(),
								transaction.getKindtransaction(),
								transaction.getCommission(),
								""
								))
												
					
					.flatMap(this.transactionDao::save);
					
	}

	@Override
	public Mono<AccountDto> deleteByIdtransaction(String id) {
		//LOGGER.info("TransactionServiceImpl");
		return this.transactionDao
				.findById(id)
				.map( p->{
					p.setStatus("DELETED");
					return p;})
				.flatMap(this.transactionDao::save)
				.flatMap(p->{
						return Mono.error(new InterruptedException("SE ELIMINO EL TRX"));
				  });
				
	}

	@Override
	public Mono<AccountDto> createdeposit (Transaction transaction) {	
		return this.accountConfig.getNumbereDepositByAccountcode(transaction.getAccountcode())
		.flatMap(q->{
			
			if(q.getKindaccount().equals("SAVINGS ACCOUNT") ||q.getKindaccount().equals("CURRENT ACCOUNT")||q.getKindaccount().equals("FIXED TERM ACCOUNT") ) {
				//Cuentas bancarias
				//En caso de que se supere el numero de depositos llegue a cero le cobrara comision
				if(q.getNumberdeposit()<=0) {					
					return this.transactionDao.save(new Transaction (
							"TRX"+UUID.randomUUID().toString(),
							transaction.getAccountcode(),												
							q.getAmount() + (transaction.getAmount()-( transaction.getAmount() * q.getCommission())),//transaction.getAmount(),
							"OK",
							new Date(),
							"DEPOSIT",
							(transaction.getAmount() * q.getCommission()),
							""
							))
							.flatMap(p -> {							
								return this.accountConfig.updateDeposit(p);
							});	
				}
				else
				{
					return this.transactionDao.save(new Transaction (
							"TRX"+UUID.randomUUID().toString(),
							transaction.getAccountcode(),												
							q.getAmount() + transaction.getAmount(),//transaction.getAmount(),,
							"OK",
							new Date(),
							"DEPOSIT",
							0.0,
							""
							))
							.flatMap(p -> {					
								return this.accountConfig.updateDeposit(p);
							});	
				}
			}
			else
			{
				//Cuentas credito no se le aplica los descuentos 
				//En caso de que se supere el numero de depositos llegue a cero le cobrara comision				
				if((q.getAmount()-transaction.getAmount())>=0)
				{
					return this.transactionDao.save(new Transaction (
							"TRX"+UUID.randomUUID().toString(),
							transaction.getAccountcode(),												
							q.getAmount() - transaction.getAmount(),//transaction.getAmount(),,
							"OK",
							new Date(),
							"DEPOSIT",
							0.0,
							""
							))
							.flatMap(p -> {					
								return this.accountConfig.updateDeposit(p);
							});	
				}
				else 
					return Mono.error(new InterruptedException("NO PUEDE PAGAR MAS DE SU DEUDA"));
			}
		});
			
	}	
	
	@Override
	public Mono<AccountDto> createretirement (Transaction transaction) {	
		
		return this.accountConfig.getNumbereDepositByAccountcode(transaction.getAccountcode())
				.flatMap(q->{
					if(q.getKindaccount().equals("SAVINGS ACCOUNT") ||q.getKindaccount().equals("CURRENT ACCOUNT")||q.getKindaccount().equals("FIXED TERM ACCOUNT") ) {
						//Cuentas bancarias
						//En caso de que se supere el numero de depositos llegue a cero le cobrara comision
						//En caso de que se supere el numero de depositos llegue a cero le cobrara comision
						if(q.getNumberretirement()<=0 && q.getAmount()>=transaction.getAmount() ) {						
								return this.transactionDao.save(new Transaction (
												//transaction.getBankname(),
//												transaction.getKindaccount(),
												"TRX"+UUID.randomUUID().toString(),	
												transaction.getAccountcode(),											
												q.getAmount() - (transaction.getAmount()-( transaction.getAmount() * q.getCommission())),//transaction.getAmount(),
												"OK",
												new Date(),
//												transaction.getPercent(),
												"RETIREMENT",
												(transaction.getAmount() * q.getCommission()),
												""//transaction.getBankname()
												))
												.flatMap(p -> {					
													return this.accountConfig.updateRetirement(p);
												});
						}
						else if(q.getNumberretirement()>0 && q.getAmount()>=transaction.getAmount())
						{
								return this.transactionDao.save(new Transaction (
									//transaction.getBankname(),
//									transaction.getKindaccount(),
									"TRX"+UUID.randomUUID().toString(),	
									transaction.getAccountcode(),											
									q.getAmount() - transaction.getAmount(),
									"OK",
									new Date(),
//									transaction.getPercent(),
									"RETIREMENT",
									0.0,
									""//transaction.getBankname()
									))
									.flatMap(p -> {					
										return this.accountConfig.updateRetirement(p);
									});
						}
						else {
							return Mono.error(new InterruptedException("NO SE PUEDE HACER ESTA OPERACION"));
						}
					}
					else {
						//En caso de que se supere el numero de depositos llegue a cero le cobrara comision
						if(( q.getAmount()+transaction.getAmount())<=q.getLimit()) {						
								return this.transactionDao.save(new Transaction (
												//transaction.getBankname(),
//												transaction.getKindaccount(),
												"TRX"+UUID.randomUUID().toString(),	
												transaction.getAccountcode(),											
												q.getAmount() + (transaction.getAmount()),//transaction.getAmount(),
												"OK",
												new Date(),
//												transaction.getPercent(),
												"RETIREMENT",
												0.0,
												""//transaction.getBankname()
												))
												.flatMap(p -> {					
													return this.accountConfig.updateRetirement(p);
												});
						}						
						else 
							return Mono.error(new InterruptedException("NO PUEDE RETIRAR MAS DE SU LIMITE"));
					}
					
					
				});
					

		 
	}

	@Override
	public Flux<Transaction> findAll() {
		return this.transactionDao.findAll();
	}
	
	
	@Override
	public Mono<AccountDto> interbanktransaction(InterbanktransactionDto interbanktransactionDto) {
		double dinterbanktransactionDto=5;		
		//buscamos la cuenta en las cuentas para ver si existe	
		return this.accountConfig.findByAccountCode(interbanktransactionDto.getAccountcode())
			.flatMap(q->{	
				if(q == null) {
					return Mono.error(new InterruptedException("ERROR"));
				}
				else {				
					return this.transactionDao.save(new Transaction (
							//"BBC poner el bando de account bank",
//							"--",
							"TRX"+UUID.randomUUID().toString(),	
							interbanktransactionDto.getAccountcode(),										
							q.getAmount() - (interbanktransactionDto.getAmount()),//transaction.getAmount(),
							"OK",
							new Date(),
//							0.0,
							"INTERBANKTRANSACTION",
							(0.0),
							interbanktransactionDto.getAccountcode()
							))
							.flatMap(p -> {					
								return this.accountConfig.updateinterbanktransaction(p)
										.flatMap(w->{
											
											//Start the deposit			
											return this.accountConfig.findByAccountCode(interbanktransactionDto.getDestinationaccountcode())
													.flatMap(rr->{	
														if(rr == null) {
															return Mono.error(new InterruptedException("ERROR"));
														}
														else {				
															return this.transactionDao.save(new Transaction (
																	//"BBC poner el bando de account bank",
//																	"--",
																	UUID.randomUUID().toString(),	
																	interbanktransactionDto.getDestinationaccountcode(),										
																	rr.getAmount() + (interbanktransactionDto.getAmount()-( interbanktransactionDto.getAmount() * 0.10)),//transaction.getAmount(),
																	"OK",
																	new Date(),
//																	0.0,
																	"INTERBANKTRANSACTION",
																	(interbanktransactionDto	.getAmount() * rr.getCommission()),
																	interbanktransactionDto.getAccountcode()
																	))
																	.flatMap(ss -> {					
																		return this.accountConfig.updateinterbanktransaction(ss);
																	});					
														}
													});
											//End the deposit 		
											
										});							 
								
							});					
				}
			});	 
	}

	@Override
	public Mono<Transaction> create(Transaction transaction) {
		return this.transactionDao.save(new Transaction (
				//transaction.getBankname(),
//				transaction.getKindaccount(),
				transaction.getId(),
				transaction.getAccountcode(),												
				transaction.getAmount(),
				transaction.getStatus(),
				new Date(),
//				transaction.getPercent(),
				transaction.getKindtransaction(),
				transaction.getCommission(),
				transaction.getDestinationaccountcode()				
				));	
	}

	@Override
	public Flux<Transaction> findByDateforFeesCharged(PeriodDto periodDto) {
   

			return this.transactionDao
					   .findByDateforFeesCharged(periodDto.getStartdate().toString(),periodDto.getEndingdate().toString());
		
	}	
}




