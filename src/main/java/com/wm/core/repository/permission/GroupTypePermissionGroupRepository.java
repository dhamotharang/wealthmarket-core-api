package com.wm.core.repository.permission;

import com.wm.core.model.permission.GroupTypePermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GroupTypePermissionGroupRepository extends JpaRepository<GroupTypePermissionGroup, Long> {

	List<GroupTypePermissionGroup> findByGrouptypeId(long grouptypeId);

	Optional<GroupTypePermissionGroup> findByGrouptypeIdAndPermissiongroupId(long grouptypeId, long permissiongroupId);
}
