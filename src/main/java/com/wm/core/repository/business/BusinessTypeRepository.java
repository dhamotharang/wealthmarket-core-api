package com.wm.core.repository.business;

import com.wm.core.model.business.BusinessType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BusinessTypeRepository extends JpaRepository<BusinessType, Long> {


	List<BusinessType> findByBusinesssectorId(long businesssectionId);

}
