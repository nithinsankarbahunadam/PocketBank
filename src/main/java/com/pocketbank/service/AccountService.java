package com.pocketbank.service;

import com.pocketbank.exception.InsufficientFundsException;
import com.pocketbank.model.Account;

public interface AccountService {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount) throws InsufficientFundsException;
    void transfer(Account from, Account to, double amount) throws InsufficientFundsException;
}
