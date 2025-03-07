package com.abc.banking_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.banking_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long >  {
	
	
	boolean existsByEmail(String email);
	boolean existsByAccountNumber(String accoutNumber);
	boolean existsById(Long userId);
	User findByAccountNumber(String accountNumber);
	User findByEmail(String email);

}
