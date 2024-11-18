package com.eteration.simplebanking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.eteration.simplebanking.controller.AccountController;
import com.eteration.simplebanking.dto.TransactionRequest;
import com.eteration.simplebanking.dto.TransactionStatus;
import com.eteration.simplebanking.exception.AccountNotFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.services.AccountService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Objects;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests  {

    @InjectMocks
    private AccountController controller;

    @Spy
    private AccountService service;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(service, "accountRepository", accountRepository);
    }


    @Test
    public void givenId_Credit_thenReturnJson()
            throws InsufficientBalanceException, AccountNotFoundException {

        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        doReturn(account).when(accountRepository).save( account);

        ResponseEntity<TransactionStatus> result = controller.credit("17892", new TransactionRequest(1000.0));

        verify(service, times(1)).findAccount("17892");
        assertEquals("OK", Objects.requireNonNull(result.getBody()).getStatus());
    }

    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson()
            throws InsufficientBalanceException, AccountNotFoundException {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<TransactionStatus> result = controller.credit( "17892", new TransactionRequest(1000.0));
        ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new TransactionRequest(50.0));
        verify(service, times(2)).findAccount("17892");
        assertEquals("OK", Objects.requireNonNull(result.getBody()).getStatus());
        assertEquals("OK", Objects.requireNonNull(result2.getBody()).getStatus());
        assertEquals(950.0, account.getBalance(),0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            Account account = new Account("Kerem Karaca", "17892");

            doReturn(account).when(service).findAccount( "17892");
            ResponseEntity<TransactionStatus> result = controller.credit( "17892", new TransactionRequest(1000.0));
            assertEquals("OK", Objects.requireNonNull(result.getBody()).getStatus());
            assertEquals(1000.0, account.getBalance(),0.001);
            verify(service, times(1)).findAccount("17892");

            ResponseEntity<TransactionStatus> result2 = controller.debit( "17892", new TransactionRequest(5000.0));
        });
    }

    @Test
    public void givenId_GetAccount_thenReturnJson()
    throws Exception {
        
        Account account = new Account("Kerem Karaca", "17892");

        doReturn(account).when(service).findAccount( "17892");
        ResponseEntity<Account> result = controller.getAccount( "17892");
        verify(service, times(1)).findAccount("17892");
        assertEquals(account, result.getBody());
    }

}
