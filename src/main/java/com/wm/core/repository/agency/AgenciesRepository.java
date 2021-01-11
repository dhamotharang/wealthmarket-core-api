package com.wm.core.repository.agency;

import com.wm.core.model.agency.Agencies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgenciesRepository extends JpaRepository<Agencies, Long> {


    Optional<Agencies> findByOrganizationId(long organizationId);

}
