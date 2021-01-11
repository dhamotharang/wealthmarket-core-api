package com.wm.core.repository.permission;

import com.wm.core.model.permission.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Long> {


    List<PermissionGroup> findByOrganizationId(long organizationId);
}
