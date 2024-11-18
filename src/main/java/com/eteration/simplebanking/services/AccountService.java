package com.eteration.simplebanking.services;


import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.model.*;
import com.eteration.simplebanking.repository.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String owner, String accountNumber) {
        return accountRepository.save(new Account(owner, accountNumber));
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    public String credit(Account account, double amount) throws InsufficientBalanceException {
        DepositTransaction transaction = new DepositTransaction(amount);
        account.post(transaction);
        accountRepository.save(account);
        return transaction.getApprovalCode();
    }

    public String debit(Account account, double amount) throws InsufficientBalanceException {
        WithdrawalTransaction transaction = new WithdrawalTransaction(amount);
        account.post(transaction);
        accountRepository.save(account);
        return transaction.getApprovalCode();
    }
}
