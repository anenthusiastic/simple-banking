package com.eteration.simplebanking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionStatus {

    private String status;

    private String approvalCode;

    public TransactionStatus(String approvalCode, String status) {
        this.approvalCode = approvalCode;
        this.status = status;
    }
}
