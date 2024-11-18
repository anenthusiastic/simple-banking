package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class WithdrawalTransaction extends Transaction {

    public WithdrawalTransaction(double amount) {
        super(amount);
        this.type = "WithdrawalTransaction";
    }

    @Override
    public void perform(Account account) throws InsufficientBalanceException {
        setAccount(account);
        account.withdraw(amount);
        setApprovalCode(UUID.randomUUID().toString());
    }

}


