package com.company.system.services;

import com.company.system.models.Account;
import com.company.system.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account updatedAccount) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount != null) {
            // Update the existing account with the data from updatedAccount
            existingAccount.setAccountNumber(updatedAccount.getAccountNumber());
            // Update other fields as needed
            return accountRepository.save(existingAccount);
        }
        return null; // Account not found
    }

    public boolean deleteAccount(Long id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount != null) {
            accountRepository.delete(existingAccount);
            return true;
        }
        return false; // Account not found
    }
}
