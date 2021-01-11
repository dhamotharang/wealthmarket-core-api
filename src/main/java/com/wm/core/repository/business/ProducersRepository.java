package com.wm.core.repository.business;

import com.wm.core.model.business.Producers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ProducersRepository extends JpaRepository<Producers, Long> {

    Optional<Producers> findByOrganizationId(long organizationId);
}
