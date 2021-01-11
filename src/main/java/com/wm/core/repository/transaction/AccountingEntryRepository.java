package com.wm.core.repository.transaction;

import com.wm.core.model.transaction.AccountingEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingEntryRepository extends JpaRepository<AccountingEntry, Long> {
}
