package com.pocketbank.model;

import com.pocketbank.exception.InsufficientFundsException;

import java.util.*;

/*
    Represents a basic bank account.
    Demonstrates arrays, ArrayList, LinkedList and Set usage.
 */
public class Account {

    private final String accountNumber;
    private final String ownerName;
    private double balance;

    //Fixed-size array for last 12 months balance
    private final double[] monthlyBalance = new double[12];

    //Transaction history (ordered) using ArrayList
    private final List<Transaction> history = new ArrayList<>();

    //Recent queue using LinkedList(FIFO)
    private final LinkedList<Transaction> recentQueue = new LinkedList<>();

    //set to keep unique transaction IDs(demonstrates set)
    private final Set<String> seenTransactionIds = new HashSet<>();

    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = initialBalance;
        //initialize monthlyBalances with the initial balance for demonstration
        for(int i = 0; i< monthlyBalance.length; i++){
            monthlyBalance[i] = initialBalance;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    //example: method declares throws -> caller must handle
    public void withdraw(double amount) throws InsufficientFundsException{
        if(amount < 0) throw new InsufficientFundsException("Amount must be positive");
        if(amount > balance){
            //throw usage - throw custom checked exception
            throw new InsufficientFundsException("Withdrawal of" + amount + "exceeds balance" + balance);
        }
        balance -= amount;
        addTransaction(new Transaction(TransactionType.WITHDRAW, amount, "Withdrawal"));
    }

    public void deposit(double amount){
        if(amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        balance += amount;
        addTransaction(new Transaction(TransactionType.DEPOSIT, amount, "Deposit"));
    }

    //Transfer uses both withdraw and deposit
    public void transferTo(Account target, double amount) throws InsufficientFundsException{
        if(target == null) throw new InsufficientFundsException("Target account cannot be null");
        //withdraw may throw InsufficientFundsException
        this.withdraw(amount);
        target.deposit(amount);
        addTransaction(new Transaction(TransactionType.TRANSFER, amount, "Transfer to " + target.getAccountNumber()));
        target.addTransaction(new Transaction(TransactionType.TRANSFER, amount, "Transfer from " + this.getAccountNumber()));
    }

    //adds transaction to structures; ensures unique transaction IDs via Set
    private void addTransaction(Transaction tx) {
        //Use the Set to avoid duplicates(unlikely with UUID, but demonstrates Set)
        if(seenTransactionIds.contains(tx.getId()))return;
        seenTransactionIds.add(tx.getId());
        history.add(tx);

        //maintain a fixed size recent queue of last 5 transactions
        recentQueue.addLast(tx);
        if(recentQueue.size()>5) recentQueue.removeFirst();
    }

    public List<Transaction> getHistory() {return new ArrayList<>(history);}
    public List<Transaction> getRecent() {return new ArrayList<>(recentQueue);}

    //array usage: rotate monthly balance (simple example: roll and set current)
    public void rollMonthlyBalance(){
        //shift left: month[0] becomes month[1],....oldest drops
        for(int i = 0; i<monthlyBalance.length -1; i++){
            monthlyBalance[i] = monthlyBalance[i+1];
        }
        monthlyBalance[monthlyBalance.length-1] = balance;
    }

    public double[] getMonthlyBalances() {
        //defensive copy
        double[] copy = new double[monthlyBalance.length];
        System.arraycopy(monthlyBalance, 0, copy, 0, monthlyBalance.length);
        return copy;
    }

    public String toString(){
        return String.format("Account[%s] owner=%s balance=%.2f", accountNumber, ownerName, balance);
    }

}
