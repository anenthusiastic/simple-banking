package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Account {

    @Id
    private String accountNumber;

    private String owner;

    private double balance;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.transactions = new ArrayList<>();
        this.balance = 0;
        this.createDate= LocalDateTime.now();
    }

    public void setBalance(double balance) throws InsufficientBalanceException {
        if (balance < 0) {
            throw new InsufficientBalanceException("Insufficient Balance!");
        }
        this.balance = balance;
    }

    public void post(Transaction transaction) throws InsufficientBalanceException {
        transaction.perform(this);
        this.transactions.add(transaction);
    }

    public void deposit(double amount) {
        try {
            setBalance(getBalance() + amount);
        } catch (InsufficientBalanceException ignored) {}
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        setBalance(getBalance() - amount);
    }
}
