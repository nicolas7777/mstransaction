package com.microservicio.app.document;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data 
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="transaction")
@ToString
public class Transaction {
//	@NotNull
//	@Size(min = 8, max = 20)
//	private String bankname;
//	@NotNull
//	@Size(min = 8, max = 20)
//	private String kindaccount;
	@Id
	private String idtransaction;
	@NotNull
	private String accountcode;
	@NotNull
	private Double amount;
	@NotNull
	@Size(min = 8, max = 20)
	private String status;
	@NotNull
	private Date date; 
//	@NotNull
//	private Double percent;
	@NotNull
	private String kindtransaction;
	@NotNull
	private Double commission;
	//@NotNull
	private String destinationaccountcode;
}
