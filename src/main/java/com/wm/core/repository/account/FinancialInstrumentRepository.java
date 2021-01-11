package com.wm.core.repository.account;

import com.wm.core.model.accounts.Instrument;
import com.wm.core.model.agency.Agencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialInstrumentRepository extends JpaRepository<Instrument, Long> {




}
