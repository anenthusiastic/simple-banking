package com.eteration.simplebanking.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PhoneBillPaymentTransaction extends PaymentTransaction {

    private String provider;

    private String phoneNumber;

    public PhoneBillPaymentTransaction(String payee, String cardNumber, String merchantName, String provider, String phoneNumber, double amount) {
        super(amount, payee, cardNumber, merchantName);
        this.provider = provider;
        this.phoneNumber = phoneNumber;
        this.type = "PhoneBillPaymentTransaction";
    }

    @Override
    protected void performPaymentTransaction() {
       setApprovalCode(UUID.randomUUID().toString());
    }

}
