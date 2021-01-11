package com.wm.core.repository.account;

import com.wm.core.model.accounts.SpecialAccountRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialAccountRuleRepository extends JpaRepository<SpecialAccountRules, Long> {

}
