package com.wm.core.repository.permission;

import com.wm.core.model.permission.RoleSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleSpecialPermissionGroupRepository extends JpaRepository<RoleSpecialPermissionGroup, Long> {

    List<RoleSpecialPermissionGroup> findByRoleId(long roleId);

    Optional<RoleSpecialPermissionGroup> findByRoleIdAndPermissiongroupId(long roleId, long permissiongroupId);
}
