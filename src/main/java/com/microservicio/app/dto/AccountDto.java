package com.microservicio.app.dto;

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
@ToString
public class AccountDto {
	private String bankname;
	private String kindaccount;
	private String accountcode;
	private Double amount;
	private String status;
	private Date date;
	private Double percent;
	private int numberdeposit;
	private int numberretirement;
	private int limit;
	private Double commission;

	
}
