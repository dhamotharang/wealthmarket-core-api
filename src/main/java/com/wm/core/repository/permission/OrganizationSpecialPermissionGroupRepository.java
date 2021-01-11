package com.wm.core.repository.permission;

import com.wm.core.model.permission.OrganizationSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface OrganizationSpecialPermissionGroupRepository extends JpaRepository<OrganizationSpecialPermissionGroup, Long> {

    List<OrganizationSpecialPermissionGroup> findByOrganizationId(long organizationId);

    Optional<OrganizationSpecialPermissionGroup> findByOrganizationIdAndPermissiongroupId(long organizationId, long permissiongroupId);

}
