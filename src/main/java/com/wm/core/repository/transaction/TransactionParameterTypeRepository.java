package com.wm.core.repository.transaction;

import com.wm.core.model.transaction.TransactionParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionParameterTypeRepository extends JpaRepository<TransactionParameterType, Long> {

}
