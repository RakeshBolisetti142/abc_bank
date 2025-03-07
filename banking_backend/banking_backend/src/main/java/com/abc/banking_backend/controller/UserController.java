package com.abc.banking_backend.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.BankStatementRequest;
import com.abc.banking_backend.service.BankStatementService;
import com.abc.banking_backend.service.UserService;
import com.itextpdf.text.DocumentException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping("api/user/")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@Autowired
	BankStatementService bankStatementService;
	
	@GetMapping("fetch_balance/{userId}")
	BankResponse fetchBalance(@PathVariable(name = "userId") Long userId) {
		return userService.getBalance(userId);
	}
	
	@PostMapping("get/statement")
	BankResponse generateStatement(@RequestBody BankStatementRequest bs) throws DocumentException, MessagingException, IOException {
		
		return bankStatementService.sendBankStatement(bs);
		
	}
	

}
