package com.wm.core.repository.permission;

import com.wm.core.model.permission.RolePermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePermissionGroupRepository extends JpaRepository<RolePermissionGroup, Long> {

	List<RolePermissionGroup> findByRoleId(long roleId);

	Optional<RolePermissionGroup> findByRoleIdAndPermissiongroupId(long roleId, long permissiongroupId);
}
