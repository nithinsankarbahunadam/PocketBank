package com.pocketbank.service;

import com.pocketbank.exception.InsufficientFundsException;
import com.pocketbank.model.Account;

public class AccountServiceImpl  implements AccountService {

    public void deposit(Account account, double amount){
        account.deposit(amount);

    }

    public void withdraw(Account account, double amount) throws InsufficientFundsException {
        account.withdraw(amount); //throws passed up(demonstrates throws propagation)
    }

    public void transfer(Account from, Account to, double amount) throws InsufficientFundsException {
        from.transferTo(to, amount);
    }
}
