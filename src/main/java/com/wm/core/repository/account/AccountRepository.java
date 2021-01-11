package com.wm.core.repository.account;

import com.wm.core.model.accounts.Account;
import com.wm.core.model.accounts.AccountType;
import com.wm.core.model.accounts.SubLedgerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByName(String name);


    List<Account> findByAccountTypeId(long accountTypeId);




}
