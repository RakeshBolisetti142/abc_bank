package com.abc.banking_backend.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.banking_backend.dto.AccountInfo;
import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.Role;
import com.abc.banking_backend.dto.UserDto;
import com.abc.banking_backend.entity.User;
import com.abc.banking_backend.repository.UserRepository;
import com.abc.banking_backend.utilities.AccountUtilities;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	
	@Autowired
	private MailService mailService;

	@Override
	public BankResponse createUserAccount(UserDto user) {
		

		if(userRepository.existsByEmail(user.getEmail())) {
			return BankResponse.builder()
					.responseCode(AccountUtilities.EXIST_RESPONSE_CODE)
					.responseMessage(AccountUtilities.EXIST_RESPONSE_MESSAGE)
					.accountInfo(null)
					.build();
		}
		
		 User createdUser = userRepository.save(User.builder()
					.firstName(user.getFirstName())
					.lastName(user.getLastName())
					.gender(user.getGender())
					.accountStatus("Active")
					.accountBalance(BigDecimal.ZERO)
					.address(user.getAddress())
					.email(user.getEmail())
					.phoneNumber(user.getPhoneNumber())
					.alternativePhoneNumber(user.getAlternativePhoneNumber())
					.stateOfOrigin(user.getStateOfOrigin())
					.accountNumber(AccountUtilities.generateAccountNumber())
					.role(Role.ROLE_USER)
					.password(bcryptEncoder.encode(user.getPassword()))
					.build());
		 
		 String subject = " Your New Bank Account is Open";
		 String body ="Dear "+createdUser.getFirstName()+" "+createdUser.getLastName()+",\n\n" +
                 "Your new bank account with ABC has been successfully opened.\n\n" +
                 "Account Number: "+createdUser.getAccountNumber()+"\n\n" +
                 "You can now use our online banking services. If you have any questions, please contact us at support@abc.com.\n\n" +
                 "Thank you for choosing ABC.\n\n" +
                 "Best Regards,\n\n" +
                 "ABC";
		 String toMail =createdUser.getEmail();
		 mailService.emailAlert(toMail, subject, body);
		 return BankResponse
				 .builder()
				 .responseCode(AccountUtilities.CREATION_RESPONSE_CODE)
				 .responseMessage(AccountUtilities.CREATION_RESPONSE_MESSAGE)
				 .accountInfo(AccountInfo
						 .builder()
						 .accountName(createdUser.getFirstName() + " "+createdUser.getLastName())
						 .accountNumber(createdUser.getAccountNumber())
						 .accoutBalance(createdUser.getAccountBalance())
						 .build())
				 .build();

		
	}
		
	

	@Override
	public BankResponse getBalance(Long userId) {
		if (!userRepository.existsById(userId)) {
			return BankResponse.builder().responseCode(AccountUtilities.USER_NOT_FOUND_CODE)
					.responseMessage(AccountUtilities.USER_NOT_FOUND_RESPONSE).accountInfo(null).build();
		}
		
		User user = userRepository.findById(userId).get();
		
		return BankResponse.builder()
				.responseCode(AccountUtilities.GET_ACCOUNT_INFO_SUCCESS_CODE)
				.responseMessage(AccountUtilities.GET_ACCOUNT_INFO_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
							.accountName(user.getFirstName() + " "+user.getLastName())
							.accountNumber(user.getAccountNumber())
							.accoutBalance(user.getAccountBalance())
							.build())
				
				.build();
	}

}
