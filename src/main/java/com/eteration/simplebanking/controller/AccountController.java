package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountRequest;
import com.eteration.simplebanking.dto.TransactionRequest;
import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create-account/")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request.getOwner(), request.getAccountNumber()));
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) throws AccountNotFoundException {
        return ResponseEntity.ok(accountService.findAccount(accountNumber));
    }

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = accountService.findAccount(accountNumber);
        String approvalCode = accountService.credit(account, request.getAmount());
        return ResponseEntity.ok(new TransactionStatus(approvalCode, "OK"));
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody TransactionRequest request) throws InsufficientBalanceException, AccountNotFoundException {
        Account account = accountService.findAccount(accountNumber);
        String approvalCode = accountService.debit(account, request.getAmount());
        return ResponseEntity.ok(new TransactionStatus(approvalCode, "OK"));
    }

}