package com.abc.banking_backend.utilities;

import java.time.Year;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AccountUtilities {

	public static Year currentYear = Year.now();
	public static int min = 100000;
	public static int max = 999999;

	public static final String CREATION_RESPONSE_CODE = "001";
	public static final String EXIST_RESPONSE_CODE = "002";

	public static final String EXIST_RESPONSE_MESSAGE = "User Account already exist.";

	public static final String CREATION_RESPONSE_MESSAGE = "User has been created successfully.";

	public static final String ACCOUNT_NOT_FOUND_RESPONSE_CODE = "003";
	public static final String ACCOUNT_NOT_FOUND_RESPONSE_MESSEAGE = "User with the provided account number is not found!";
	public static final String AMOUNT_CREDITED_RESPONSE_CODE = "004";
	public static final String AMOUNT_CREDITED_RESPONSE_MESSAGE = "Amount has been credited successfully.";

	public static final String INSUFFICIENT_BALANCE_RESPONSE_CODE = "005";
	public static final String INSUFFICIENT_BALANCE_RESPONSE_MESSAGE = "User with Insufficient Balance";

	public static final String AMOUNT_DEBITED_RESPONSE_CODE = "006";
	public static final String AMOUNT_DEBITED_RESPONSE_MESSAGE = "Amount has been debited successfully.";
	public static final String TRANSFER_SUCCESS_RESPONSE_CODE = "007";
	public static final String TRANSFER_SUCCESS_RESPONSE_MESSAGE = "Amount has been Transfered successfully.";
	public static final String STATEMENT_GENERATED_SUCCESS_CODE="008";
	public static final String STATEMENT_GENERATED_SUCCESS_RESPONSE="Your Statement has been generated successfully.";
	public static final String USER_NOT_FOUND_CODE = "009";
	public static final String USER_NOT_FOUND_RESPONSE = "No User Found !";
	
	public static final String GET_ACCOUNT_INFO_SUCCESS_CODE = "010";
	public static final String GET_ACCOUNT_INFO_SUCCESS_MESSAGE = "Account information has been fetched successfully.";
	
	

	public static String generateAccountNumber() {

		int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

		return "" + currentYear + "" + randomNumber;
	}

}
