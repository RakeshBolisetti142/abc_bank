package com.abc.banking_backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.CreditAndDebitRequest;
import com.abc.banking_backend.dto.TransferRequest;
import com.abc.banking_backend.entity.Transaction;
import com.abc.banking_backend.entity.User;
import com.abc.banking_backend.repository.UserRepository;
import com.abc.banking_backend.utilities.AccountUtilities;

import jakarta.transaction.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private MailService mailService;

	public BankResponse creditAmount(CreditAndDebitRequest request) {

		if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {
			return BankResponse.builder().responseCode(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_CODE)
					.responseMessage(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_MESSEAGE).accountInfo(null).build();
		}

		User userToBeCredited = userRepository.findByAccountNumber(request.getAccountNumber());
		userToBeCredited.setAccountBalance(userToBeCredited.getAccountBalance().add(request.getAmount()));
		List<Transaction> transactions = userToBeCredited.getTransactions();

		transactions.add(Transaction.builder().amount(request.getAmount()).type("Credit").status("Success")
				.transactionDate(LocalDate.now()).build());

		String subject = "Credit Alert: Recent Activity on Your Account";
		String body = "Dear " + userToBeCredited.getFirstName() + " " + userToBeCredited.getLastName() + ",\n\n"
				+ "We wanted to alert you to a recent credit activity on your account with ABC.\n\n"
				+ "Transaction Amount: " + request.getAmount() + "\n" + "Transaction Date: "
				+ userToBeCredited.getUpdatedAt() + "\n"
				+ "If you did not authorize this transaction or have any questions, please contact us immediately at support@abc.com.\n\n"
				+ "Thank you for choosing ABC.\n\n" + "Best Regards,\n\n" + "ABC";
		String toMail = userToBeCredited.getEmail();
		mailService.emailAlert(toMail, subject, body);
		userRepository.save(userToBeCredited);

		return BankResponse.builder().responseCode(AccountUtilities.AMOUNT_CREDITED_RESPONSE_CODE)
				.responseMessage(AccountUtilities.AMOUNT_CREDITED_RESPONSE_MESSAGE).accountInfo(null).build();
	}

	public BankResponse debitAmount(CreditAndDebitRequest request) {
		if (!userRepository.existsByAccountNumber(request.getAccountNumber())) {
			return BankResponse.builder().responseCode(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_CODE)
					.responseMessage(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_MESSEAGE).accountInfo(null).build();
		}
		User userToBeDebited = userRepository.findByAccountNumber(request.getAccountNumber());
		if (userToBeDebited.getAccountBalance().compareTo(request.getAmount()) < 0) {
			return BankResponse.builder().responseCode(AccountUtilities.INSUFFICIENT_BALANCE_RESPONSE_CODE)
					.responseMessage(AccountUtilities.INSUFFICIENT_BALANCE_RESPONSE_MESSAGE).accountInfo(null).build();

		}
		userToBeDebited.setAccountBalance(userToBeDebited.getAccountBalance().subtract(request.getAmount()));
		List<Transaction> transactions = userToBeDebited.getTransactions();

		transactions.add(Transaction.builder().amount(request.getAmount()).type("Debit").status("Success")
				.transactionDate(LocalDate.now()).build());
		String subject = "Debit Alert: Recent Activity on Your Account";
		String body = "Dear " + userToBeDebited.getFirstName() + " " + userToBeDebited.getLastName() + ",\n\n"
				+ "We wanted to alert you to a recent debit activity on your account with ABC.\n\n"
				+ "Transaction Amount: " + request.getAmount() + "\n" + "Transaction Date: "
				+ userToBeDebited.getUpdatedAt() + "\n\n"
				+ "If you did not authorize this transaction or have any questions, please contact us immediately at support@abc.com.\n\n"
				+ "Thank you for choosing ABC.\n\n" + "Best Regards,\n\n" + "ABC";
		String toMail = userToBeDebited.getEmail();

		mailService.emailAlert(toMail, subject, body);

		userRepository.save(userToBeDebited);

		return BankResponse.builder().responseCode(AccountUtilities.AMOUNT_DEBITED_RESPONSE_CODE)
				.responseMessage(AccountUtilities.AMOUNT_DEBITED_RESPONSE_MESSAGE).build();
	}

	@Transactional
	public BankResponse transferAmount(TransferRequest request) {
		if (!userRepository.existsByAccountNumber(request.getDestinationAccountNumber())) {
			return BankResponse.builder().responseCode(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_CODE)
					.responseMessage(AccountUtilities.ACCOUNT_NOT_FOUND_RESPONSE_MESSEAGE).accountInfo(null).build();
		}
		User sourceUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
		if (sourceUser.getAccountBalance().compareTo(request.getAmount()) < 0) {
			return BankResponse.builder().responseCode(AccountUtilities.INSUFFICIENT_BALANCE_RESPONSE_CODE)
					.responseMessage(AccountUtilities.INSUFFICIENT_BALANCE_RESPONSE_MESSAGE).accountInfo(null).build();

		}
		sourceUser.setAccountBalance(sourceUser.getAccountBalance().subtract(request.getAmount()));
		List<Transaction> SourceUsertransactions = sourceUser.getTransactions();
		SourceUsertransactions.add(Transaction.builder().amount(request.getAmount()).type("Debit").status("Success")
				.transactionDate(LocalDate.now()).build());
		String subject = "Debit Alert: Recent Activity on Your Account";
		String body = "Dear " + sourceUser.getFirstName() + " " + sourceUser.getLastName() + ",\n\n"
				+ "We wanted to alert you to a recent debit activity on your account with ABC.\n\n"
				+ "Transaction Amount: " + request.getAmount() + "\n" + "Transaction Date: " + sourceUser.getUpdatedAt()
				+ "\n\n"
				+ "If you did not authorize this transaction or have any questions, please contact us immediately at support@abc.com.\n\n"
				+ "Thank you for choosing ABC.\n\n" + "Best Regards,\n\n" + "ABC";
		String toMail = sourceUser.getEmail();
		mailService.emailAlert(toMail, subject, body);

		userRepository.save(sourceUser);

		User destinationUser = userRepository.findByAccountNumber(request.getDestinationAccountNumber());
		destinationUser.setAccountBalance(destinationUser.getAccountBalance().add(request.getAmount()));
		List<Transaction> DestinationUsertransactions = destinationUser.getTransactions();

		DestinationUsertransactions.add(Transaction.builder().amount(request.getAmount()).type("Credit")
				.status("Success").transactionDate(LocalDate.now()).build());
		subject = "Credit Alert: Recent Activity on Your Account";
		body = "Dear " + destinationUser.getFirstName() + " " + destinationUser.getLastName() + ",\n\n"
				+ "We wanted to alert you to a recent credit activity on your account with ABC.\n\n"
				+ "Transaction Amount: " + request.getAmount() + "\n" + "Transaction Date: "
				+ destinationUser.getUpdatedAt() + "\n"
				+ "If you did not authorize this transaction or have any questions, please contact us immediately at support@abc.com.\n\n"
				+ "Thank you for choosing ABC.\n\n" + "Best Regards,\n\n" + "ABC";
		toMail = destinationUser.getEmail();
		mailService.emailAlert(toMail, subject, body);

		userRepository.save(destinationUser);

		return BankResponse.builder().responseCode(AccountUtilities.TRANSFER_SUCCESS_RESPONSE_CODE)
				.responseMessage(AccountUtilities.TRANSFER_SUCCESS_RESPONSE_MESSAGE).accountInfo(null).build();
	}

}
