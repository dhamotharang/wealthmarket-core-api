package com.wm.core.repository.business;

import com.wm.core.model.business.BusinessSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BusinessSectorRepository extends JpaRepository<BusinessSector, Long> {



}
