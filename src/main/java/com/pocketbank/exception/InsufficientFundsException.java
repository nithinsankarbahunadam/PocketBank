package com.pocketbank.exception;


/*
    Custom checked exception : forces caller to handle or declare it.
     */
public class InsufficientFundsException extends Exception {

        public InsufficientFundsException(String message) {
            super(message);
        }
}
