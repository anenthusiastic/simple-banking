package com.eteration.simplebanking.repository;

import com.eteration.simplebanking.model.WithdrawalTransaction;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawalTransactionRepository extends TransactionRepository<WithdrawalTransaction> {


}