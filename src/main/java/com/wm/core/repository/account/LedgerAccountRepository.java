package com.wm.core.repository.account;

import com.wm.core.model.accounts.LedgerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount, Long> {
	Optional<LedgerAccount> findByName(String name);
}
