package com.wm.core.repository.account;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountingRuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountingRuleTypeRepository extends JpaRepository<AccountingRuleType, Long> {





}
