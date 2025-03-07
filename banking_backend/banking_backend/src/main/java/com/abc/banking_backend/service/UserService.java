package com.abc.banking_backend.service;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.UserDto;


public interface UserService {
	
	BankResponse createUserAccount(UserDto userDto);

	BankResponse getBalance(Long userId);

}
