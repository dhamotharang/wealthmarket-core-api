package com.wm.core.repository.account;

import com.wm.core.model.accounts.UserAccountRule;
import org.hibernate.validator.constraints.EAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRuleRepository extends JpaRepository<UserAccountRule, Long> {

    Optional<UserAccountRule> findByAccountIdAndRuleId(long accountId, long ruleId);

    List<UserAccountRule> findByAccountId(long accountId);
}
