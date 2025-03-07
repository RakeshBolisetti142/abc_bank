package com.abc.banking_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.CreditAndDebitRequest;
import com.abc.banking_backend.dto.TransferRequest;
import com.abc.banking_backend.service.TransferService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/user/")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class TransferController {
	TransferService transferService;
	

	@Autowired
	public TransferController(TransferService transferService) {
		System.out.println("Testing Git");
		this.transferService = transferService;
		
	}
	@PutMapping("credit")
	public BankResponse creditAmountController(@RequestBody CreditAndDebitRequest request) {
		
		return transferService.creditAmount(request);
		
	}
	@PutMapping("debit")
	public BankResponse debitAmountController(@RequestBody CreditAndDebitRequest request) {
		
		return transferService.debitAmount(request);
		
	}
	@PutMapping("transfer")
	public BankResponse TransferAmountControlleir(@RequestBody TransferRequest request) {
		return transferService.transferAmount(request);
	}
		
}
