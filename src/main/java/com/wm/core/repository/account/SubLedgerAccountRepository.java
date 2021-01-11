package com.wm.core.repository.account;

import com.wm.core.model.accounts.SubLedgerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubLedgerAccountRepository extends JpaRepository<SubLedgerAccount, Long> {
	Optional<SubLedgerAccount> findByName(String name);


	List<SubLedgerAccount> findByLedgerId(long ledgerId);

}
