package com.abc.banking_backend.secure.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abc.banking_backend.dto.BankResponse;
import com.abc.banking_backend.dto.UserDto;
import com.abc.banking_backend.entity.User;
import com.abc.banking_backend.repository.UserRepository;
import com.abc.banking_backend.service.MailService;
import com.abc.banking_backend.service.UserService;


@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailService mailService;
//	Date date =new Date();

	@Autowired
	private UserService userService;

	private String roles;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
		System.out.println("Hii");
		User user = userRepository.findByEmail(email);
		System.out.println(user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
		roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole().toString()));
		System.out.println("Roles : "+roles);
		setRoles(roles.toString());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				roles);
		
	}
	

	public String getRoles() {
		return roles;
	}


	public void setRoles(String roles) {
		this.roles = roles;
	}


	public BankResponse save(UserDto user) {

//		 return userDao.save(User.builder()
//					.firstName(user.getFirstName())
//					.lastName(user.getLastName())
//					.gender(user.getGender())
//					.accountStatus("Active")
//					.accountBalance(BigDecimal.ZERO)
//					.address(user.getAddress())
//					.email(user.getEmail())
//					.phoneNumber(user.getPhoneNumber())
//					.alternativePhoneNumber(user.getAlternativePhoneNumber())
//					.stateOfOrigin(user.getStateOfOrigin())
//					.accountNumber(AccountUtilities.generateAccountNumber())
//					.role(Role.ROLE_USER)
//					.password(bcryptEncoder.encode(user.getPassword()))
//					.build());
		
		return userService.createUserAccount(user);
	}
	}
