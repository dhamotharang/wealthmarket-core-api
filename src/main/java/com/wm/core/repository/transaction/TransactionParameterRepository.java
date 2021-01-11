package com.wm.core.repository.transaction;

import com.wm.core.model.transaction.TransactionParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionParameterRepository extends JpaRepository<TransactionParameter, Long> {

    List<TransactionParameter> findByTransactionParameterTypeId(long transactionParameterTypeId);

}