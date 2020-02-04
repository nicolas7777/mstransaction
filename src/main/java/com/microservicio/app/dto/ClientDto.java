package com.microservicio.app.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data 
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDto {	
	private String id;
	private String firstname;
	private String lastname;
	private String kindclient;
	private String documentnumber;	
	private Date date;
	private String status;
	
	public AccountDto accountdto;
}
