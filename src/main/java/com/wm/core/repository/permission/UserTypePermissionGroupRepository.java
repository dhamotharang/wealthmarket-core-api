package com.wm.core.repository.permission;

import com.wm.core.model.permission.UserTypePermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTypePermissionGroupRepository extends JpaRepository<UserTypePermissionGroup, Long> {

	List<UserTypePermissionGroup> findByUsertypeId(long usertypeId);

	Optional<UserTypePermissionGroup> findByUsertypeIdAndAndPermissiongroupId(long usertypeId, long permissiongroupId);

}
