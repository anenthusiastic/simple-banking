package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PaymentTransaction extends Transaction {

    protected String payee;

    protected String cardNumber;

    protected String merchantName;

    public PaymentTransaction(double amount, String payee, String cardNumber, String merchantName) {
        super(amount);
        this.payee = payee;
        this.cardNumber = cardNumber;
        this.merchantName = merchantName;
    }

    @Override
    protected void perform(Account account) throws InsufficientBalanceException {
        setAccount(account);
        account.withdraw(amount);
        performPaymentTransaction();
    }

    protected abstract void performPaymentTransaction();
}
