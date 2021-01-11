package com.wm.core.repository.account;

import com.wm.core.model.accounts.AccountingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountingRuleRepository extends JpaRepository<AccountingRule, Long> {


    List<AccountingRule> findByRuleTypeId(long ruleTypeId);





}
