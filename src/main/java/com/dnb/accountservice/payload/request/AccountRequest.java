package com.dnb.accountservice.payload.request;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRequest {

	@Column(nullable = false)
	@NotBlank(message = "Account Holder Name should not be blank")
	private String accountHolderName;
	@NotBlank(message = "AccountType should not be blank")
	private String accountType;
	@Min(value = 0, message = "Balance shouldnot be negative")
	private float balance;
	@Length(min = 10, max = 10)
	@Pattern(regexp = "^[0-9]{10}$")
	private String contactNumber;
	@NotBlank(message = "Address should not be blank")
	private String address;
	@NotNull(message = "DOB must be provided")
	private LocalDate dob;
//	private Customer customer;
	private Integer customerId;
}
