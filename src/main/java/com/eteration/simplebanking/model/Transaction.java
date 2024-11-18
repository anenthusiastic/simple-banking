package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    protected LocalDateTime date;

    protected double amount;

    protected String type;

    protected String approvalCode;

    @ManyToOne
    @JoinColumn(name = "account_number")
    @JsonIgnore
    protected Account account;

    public Transaction(double amount) {
        this.date = LocalDateTime.now();
        this.amount = amount;
    }

    protected abstract void perform(Account account) throws InsufficientBalanceException;

}
