package com.wm.core.repository.business;

import com.wm.core.model.business.Businesses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessesRepository extends JpaRepository<Businesses, Long> {


	List<Businesses> findByBusinesstypeId(long businestypeId);

	List<Businesses> findByBusinesssectorId(long businesssectorId);

	Optional<Businesses> findByOrganizationId(long organizationId);
}
