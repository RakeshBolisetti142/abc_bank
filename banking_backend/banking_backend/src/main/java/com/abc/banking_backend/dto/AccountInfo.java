package com.abc.banking_backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {
	
	private String accountName;
	private BigDecimal accoutBalance;
	private  String accountNumber;
	

}
