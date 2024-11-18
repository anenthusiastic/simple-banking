package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.DepositTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTransactionRepository extends TransactionRepository<DepositTransaction> {

}