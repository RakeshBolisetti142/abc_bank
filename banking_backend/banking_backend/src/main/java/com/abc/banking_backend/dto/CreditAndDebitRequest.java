package com.abc.banking_backend.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAndDebitRequest {
	private String accountNumber;
	private BigDecimal amount;
	private String paymentType;	
}
