package com.pocketbank.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private final String id;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final String note;

    public Transaction(TransactionType type,double amount, String note){
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.note = note;
    }


    public String getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getNote() {
        return note;
    }

    public String toString(){
        return String.format("Transaction[%s] %s %.2f at %s (%s)", id, type, amount, timestamp, note);
    }
}
