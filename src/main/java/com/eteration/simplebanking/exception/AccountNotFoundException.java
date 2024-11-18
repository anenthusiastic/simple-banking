package com.eteration.simplebanking.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String accountNumber) {
        super(String.format("Account with id %s not found!", accountNumber));
    }
}
