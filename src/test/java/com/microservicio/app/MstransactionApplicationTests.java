package com.microservicio.app;

import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.microservicio.app.document.Transaction;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MstransactionApplicationTests {

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	@Test
	void contextLoads() {
	}
	
	@Autowired
	private WebTestClient webTestClient;
	
	//CRUD:save
		@Test
		void saveBank() {
				//mongoTemplate.dropCollection("transaction").subscribe();
				//String sUUID = UUID.randomUUID().toString();
				Transaction transaction = new Transaction(				
						"TRX"+UUID.randomUUID().toString(),// idtransaction;
						"ACC"+UUID.randomUUID().toString(),//accountcode
						10.0,// amount;					
						"OK", //status					
						null, //date					
						"DEPOSIT", //kindtransaction;					
						0.8,// commission;	
						""
						);
				webTestClient.post()
				.uri("/transaction/create")
				.body(Mono.just(transaction), Transaction.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Transaction.class)
				.consumeWith(response -> {
					Transaction transactionres = response.getResponseBody();
					System.out.println("[Cliente registrado] " + transaction);
					//Assertions.assertThat(clientres.getId()).isNotNull().isEqualTo(sUUID);			
					Assertions.assertThat(transactionres.getId()).isNotNull().isEqualTo("11111111111111111111");
					Assertions.assertThat(transactionres.getAccountcode()).isNotNull().isEqualTo("AAAAAA");
					Assertions.assertThat(transactionres.getAmount()).isNotNull().isEqualTo(10.0);
					Assertions.assertThat(transactionres.getStatus()).isNotNull().isEqualTo("OK");
					Assertions.assertThat(transactionres.getKindtransaction()).isNotNull().isEqualTo("DEPOSIT");
					Assertions.assertThat(transactionres.getCommission()).isNotNull().isEqualTo(0.8);
					Assertions.assertThat(transactionres.getDestinationaccountcode()).isNotNull().isEqualTo("BBBBBB");
				});
		}
				

}
