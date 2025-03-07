package com.abc.banking_backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("$(spring.mail.username)")
	private String senderEmail;

	@Override
	public void emailAlert(String toEmail,String subject,String body) {
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(senderEmail);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(body);
		mailSender.send(mailMessage);
		
		
		
	}

	@Override
	public void sendStatement(String toEmail, String subject, String body, ByteArrayResource attachment) throws MessagingException, IOException {
//				
				MimeMessage message=mailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message,true);
				helper.setTo(toEmail);
				helper.setFrom(senderEmail);
				helper.setSubject(subject);
				helper.setText(body);
				helper.addAttachment("bank_statement.pdf",attachment);
					
				mailSender.send(message);
				
	}
	

}
