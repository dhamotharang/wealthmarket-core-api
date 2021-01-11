package com.wm.core.repository.transaction;

import com.wm.core.model.transaction.TransactionParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionPartyRepository extends JpaRepository<TransactionParty, Long> {
}
