package com.pocketbank.app;

import com.pocketbank.exception.InsufficientFundsException;
import com.pocketbank.model.Account;
import com.pocketbank.model.Transaction;
import com.pocketbank.service.AccountService;
import com.pocketbank.service.AccountServiceImpl;

import java.util.List;

public class FinanceApp {
    public static void main(String[] args) {
        Account acctA = new Account("ACC1001", "Alice", 1000.0);
        Account acctB = new Account("ACC1002", "Bob", 500.0);

        AccountService service = new AccountServiceImpl();

        // deposit
        service.deposit(acctA, 250.0);
        // withdraw in try/catch — demonstrates handling checked custom exception
        try {
            service.withdraw(acctA, 200.0);
        } catch (InsufficientFundsException e) {
            System.err.println("Withdraw failed: " + e.getMessage());
        }

        // transfer with insufficient funds handling
        try {
            System.out.println("Attempting transfer of 2000.0 from Alice to Bob...");
            service.transfer(acctA, acctB, 2000.0);
        } catch (InsufficientFundsException e) {
            System.err.println("Transfer failed: " + e.getMessage());
        }

        // valid transfer
        try {
            System.out.println("Transferring 300.0 Alice -> Bob");
            service.transfer(acctA, acctB, 300.0);
        } catch (InsufficientFundsException e) {
            System.err.println("Transfer failed: " + e.getMessage());
        }

        // roll month and print monthly balances (array usage)
        acctA.rollMonthlyBalance();
        double[] mb = acctA.getMonthlyBalances();
        System.out.println("Alice monthly balances (latest last):");
        for (int i = 0; i < mb.length; i++) {
            System.out.printf("  month %2d: %.2f%n", i + 1, mb[i]);
        }

        // print transaction history (ArrayList)
        System.out.println("\nAlice transaction history:");
        List<Transaction> historyA = acctA.getHistory();
        for (Transaction t : historyA) System.out.println("  " + t);

        System.out.println("\nBob transaction history:");
        for (Transaction t : acctB.getHistory()) System.out.println("  " + t);

        // demonstrate LinkedList recent queue
        System.out.println("\nAlice recent transactions:");
        acctA.getRecent().forEach(t -> System.out.println("  " + t));
    }
}
