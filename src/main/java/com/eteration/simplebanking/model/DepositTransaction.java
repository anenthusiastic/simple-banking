package com.eteration.simplebanking.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
        super.setType("DepositTransaction");
    }

    @Override
    public void perform(Account account) {
        setAccount(account);
        account.deposit(amount);
        setApprovalCode(UUID.randomUUID().toString());
    }

}
