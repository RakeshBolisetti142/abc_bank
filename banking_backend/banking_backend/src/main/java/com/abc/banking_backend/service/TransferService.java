package com.abc.banking_backend.service;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.CreditAndDebitRequest;
import com.abc.banking_backend.dto.TransferRequest;

public interface TransferService {
	
	BankResponse creditAmount(CreditAndDebitRequest request);
	BankResponse debitAmount(CreditAndDebitRequest request);
	BankResponse transferAmount(TransferRequest request);
	
	

}
