package com.abc.banking_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

@NotBlank(message =  "User Name required")
    
    private String firstName;
	private String lastName;
	private String gender;
	private String address;
	private String stateOfOrigin;
	private String email;
	private String password;
	private String phoneNumber;
	private String alternativePhoneNumber;
    
   

}