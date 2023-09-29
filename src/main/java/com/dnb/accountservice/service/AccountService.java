package com.dnb.accountservice.service;

import java.util.List;
import java.util.Optional;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.exceptions.IdNotFoundException;

public interface AccountService {

	public Account createAccount(Account account) throws IdNotFoundException;

	public Optional<Account> getAccountById(String accountId);

	public boolean deleteAccountById(String accountId) throws IdNotFoundException;

	public Iterable<Account> getAllAccounts();

	public boolean checkAccountExistsById(String accountId);

	public List<Account> getAccountByContactNumber(String contactNumber);
}
