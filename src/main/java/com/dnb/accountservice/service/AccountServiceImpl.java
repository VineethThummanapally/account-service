package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.dto.Customer;
import com.dnb.accountservice.exceptions.IdNotFoundException;
import com.dnb.accountservice.repository.AccountRepository;

@Service("accountServiceImpl")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RestTemplate restTemplate;

//	@Autowired
//	private APIClient apiClient;

	@Value("${api.customer}")
	private String URL;

	@Override
	public Account createAccount(Account account) throws IdNotFoundException {

		try {
			ResponseEntity<Customer> responseEntity = restTemplate
					.getForEntity(URL + "/" + String.valueOf(account.getCustomerId()), Customer.class);
			System.out.println(responseEntity.getBody());

//			Customer customer = apiClient.getCustomerById(account.getCustomerId());
			return accountRepository.save(account);

		} catch (Exception e) {
			throw new IdNotFoundException("Customer Not Found");
		}

	}

	@Override
	public Optional<Account> getAccountById(String accountId) {

		return accountRepository.findById(accountId);
	}

	@Override
	public boolean deleteAccountById(String accountId) throws IdNotFoundException {
		boolean isExists = accountRepository.existsById(accountId);
		if (!isExists) {
			throw new IdNotFoundException("Id Not Found..");
		}
		accountRepository.deleteById(accountId);

		if (accountRepository.existsById(accountId))
			return false;
		else
			return true;
	}

	@Override
	public Iterable<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public boolean checkAccountExistsById(String accountId) {
		return accountRepository.existsById(accountId);
	}

	@Override
	public List<Account> getAccountByContactNumber(String contactNumber) {
		return accountRepository.findByContactNumber(contactNumber);
	}
}
