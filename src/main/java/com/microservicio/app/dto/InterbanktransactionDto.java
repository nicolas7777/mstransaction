package com.microservicio.app.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InterbanktransactionDto {
	private String accountcode;//it is the code of origin
	private Double amount;
	private String destinationaccountcode;//it is the code of destination

}
