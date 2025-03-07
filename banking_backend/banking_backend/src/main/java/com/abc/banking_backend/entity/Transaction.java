package com.abc.banking_backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {
	@Column(name = "transaction_id")
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@Column(name="transaction_amount")
	private BigDecimal amount;
	@Column(name="transaction_type")
	private String type;
	@Column(name="transaction_status")
	private String status;
	private LocalDate transactionDate;
}