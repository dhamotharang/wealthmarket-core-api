package com.wm.core.repository.transaction;

import com.wm.core.model.transaction.TransactionCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionChargeRepository extends JpaRepository<TransactionCharge, Long> {
}
