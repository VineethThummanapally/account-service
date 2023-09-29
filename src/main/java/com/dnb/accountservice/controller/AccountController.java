package com.dnb.accountservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.payload.request.AccountRequest;
import com.dnb.accountservice.service.AccountService;
import com.dnb.accountservice.utils.RequestToEntityMapper;

import jakarta.validation.Valid;

@RefreshScope
@RestController // rest controller is derived from controller + response
@RequestMapping("/account")
public class AccountController {

	// insert/ create account : post : @PostMapping
	@Autowired
	private AccountService accountService;

	@Value("${customProperty.test}")
	private String test;

	@Autowired
	private RequestToEntityMapper mapper;

	@GetMapping("/test")
	public ResponseEntity<String> getTest() {
		return ResponseEntity.ok(test);
	}

	@DeleteMapping("/{accountId}")
	public ResponseEntity<?> deleteAccountById(@PathVariable String accountId) throws IdNotFoundException {

		if (accountService.checkAccountExistsById(accountId)) {
			accountService.deleteAccountById(accountId);
			return ResponseEntity.noContent().build();
		} else {
			throw new IdNotFoundException("Id Not Found");
		}
	}

	// path variable
	@GetMapping("/{accountId}") // it should help us to get the specific account details
	public ResponseEntity<?> getAccountById(@PathVariable("accountId") String accountId) throws IdNotFoundException {

		Optional<Account> requestedAccount = accountService.getAccountById(accountId);
		if (requestedAccount.isPresent())
			return ResponseEntity.ok(requestedAccount.get());
		else {
			throw new IdNotFoundException("Requested Id Info Not found");
		}
	}

	@PostMapping("/create") // combination of @RequestMapping + PostMethod => spring 4.3.
	public ResponseEntity<?> creatAccount(@Valid @RequestBody AccountRequest accountRequest)
			throws IdNotFoundException {

		Account account = mapper.getAccountEntityObject(accountRequest);

		try {
			Account createdAccount = accountService.createAccount(account);
			return new ResponseEntity<Account>(createdAccount, HttpStatus.CREATED);
		} catch (IdNotFoundException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
			throw new IdNotFoundException(e.getMessage());
		}
	}

	@GetMapping("/allAccounts/{contactNumber:^[0-9]{10}$}")
	public ResponseEntity<?> getAccountByContactNumber(@PathVariable String contactNumber) throws IdNotFoundException {
		List<Account> ContactNumberedAccount = accountService.getAccountByContactNumber(contactNumber);

		if (ContactNumberedAccount.size() != 0)
			return ResponseEntity.ok(ContactNumberedAccount);
		else {
			throw new IdNotFoundException("Requested contact number Info Not found");
		}
	}

	@GetMapping("/allAccounts")
	public ResponseEntity<?> getAllAccounts() throws IdNotFoundException {
		List<Account> allAccounts = (List<Account>) accountService.getAllAccounts();

		if (allAccounts.size() != 0)
			return ResponseEntity.ok(allAccounts);
		else {
			throw new IdNotFoundException("No Accounts Found ");
		}
	}
}
