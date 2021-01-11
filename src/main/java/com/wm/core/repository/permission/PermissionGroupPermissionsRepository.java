package com.wm.core.repository.permission;

import com.wm.core.model.permission.PermissionGroupPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionGroupPermissionsRepository extends JpaRepository<PermissionGroupPermission, Long> {

	List<PermissionGroupPermission> findByPermissiongroupId(long permissiongroupId);


	Optional<PermissionGroupPermission> findByPermissiongroupIdAndPermissionId(long permissiongroupId, long permissionId);
}
