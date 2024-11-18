package com.eteration.simplebanking.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
