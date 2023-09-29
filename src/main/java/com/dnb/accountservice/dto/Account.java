package com.dnb.accountservice.dto;

import java.time.LocalDate;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import com.dnb.accountservice.utils.CustomIdGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")

	@GenericGenerator(name = "account_seq", type = CustomIdGenerator.class, parameters = {
			@Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "50"),
			@Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "ACC_"),
			@Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
	private String accountId;

	@Column(nullable = false)
	@NotBlank(message = "Account Holder Name should not be blank")
	private String accountHolderName;

	@NotBlank(message = "AccountType should not be blank")
	private String accountType;

	@Min(value = 0, message = "Balance should not be negative")
	private float balance;

	@Length(min = 10, max = 10)
	@Pattern(regexp = "^[0-9]{10}$")
	private String contactNumber;

	@NotBlank(message = "Address should not be blank")
	private String address;

	private LocalDate accountCreatedDate = LocalDate.now();

	@NotNull(message = "DOB must be provided")
	private LocalDate dob;

	@Transient
	private boolean accountStatus = true;

	private int customerId;
}
