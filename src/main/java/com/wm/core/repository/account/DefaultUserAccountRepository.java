package com.wm.core.repository.account;

import com.wm.core.model.accounts.DefaultUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultUserAccountRepository extends JpaRepository<DefaultUserAccount, Long> {






}
