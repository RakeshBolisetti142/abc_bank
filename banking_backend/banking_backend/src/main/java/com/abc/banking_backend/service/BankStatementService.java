package com.abc.banking_backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.BankStatementRequest;
import com.abc.banking_backend.entity.Transaction;
import com.abc.banking_backend.entity.User;
import com.itextpdf.text.DocumentException;

import jakarta.mail.MessagingException;

public interface BankStatementService {

	BankResponse sendBankStatement(BankStatementRequest bs) throws DocumentException, MessagingException, IOException;

	ByteArrayResource generateBankStatement(User user, List<Transaction> transactionsFromSpecificRange)
			throws DocumentException;
}
