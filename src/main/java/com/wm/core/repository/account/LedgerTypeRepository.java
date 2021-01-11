package com.wm.core.repository.account;

import com.wm.core.model.accounts.LedgerType;
import com.wm.core.model.agency.Agencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerTypeRepository extends JpaRepository<LedgerType, Long> {






}
