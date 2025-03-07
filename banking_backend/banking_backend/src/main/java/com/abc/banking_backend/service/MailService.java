package com.abc.banking_backend.service;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;

import jakarta.mail.MessagingException;

public interface MailService {
	
	
	void emailAlert(String toEmail,String subject,String body);
	void sendStatement(String toEmail,String subject,String body,ByteArrayResource attachment) throws MessagingException,IOException;
	
}
