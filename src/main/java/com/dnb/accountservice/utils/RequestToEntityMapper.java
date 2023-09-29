package com.dnb.accountservice.utils;

import org.springframework.stereotype.Component;

import com.dnb.accountservice.dto.Account;
import com.dnb.accountservice.payload.request.AccountRequest;

@Component
public class RequestToEntityMapper {

	public Account getAccountEntityObject(AccountRequest accountRequest) {

		Account account = new Account();

		account.setAccountHolderName(accountRequest.getAccountHolderName());
		account.setAccountType(accountRequest.getAccountType());
		account.setAddress(accountRequest.getAddress());
		account.setBalance(accountRequest.getBalance());
		account.setContactNumber(accountRequest.getContactNumber());
		account.setDob(accountRequest.getDob());
		account.setCustomerId(accountRequest.getCustomerId());

		return account;
	}

}
