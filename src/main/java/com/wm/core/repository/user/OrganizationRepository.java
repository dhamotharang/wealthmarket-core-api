package com.wm.core.repository.user;

import com.wm.core.model.user.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    List<Organization> findByGrouptypeId(long grouptypeId);

    List<Organization> findByGroupId(long groupId);

    List<Organization> findBySubgroupId(long subgroupId);




}
