package com.wm.core.repository.permission;

import com.wm.core.model.permission.GroupPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GroupPermissionGroupRepository extends JpaRepository<GroupPermissionGroup, Long> {

    List<GroupPermissionGroup> findByGroupId(long groupId);

    Optional<GroupPermissionGroup> findByGroupIdAndAndPermissiongroupId(long groupId, long permissiongroupId);


}
