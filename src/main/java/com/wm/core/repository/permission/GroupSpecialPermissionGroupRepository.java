package com.wm.core.repository.permission;

import com.wm.core.model.permission.GroupSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GroupSpecialPermissionGroupRepository extends JpaRepository<GroupSpecialPermissionGroup, Long> {

    List<GroupSpecialPermissionGroup> findByGroupId(long groupId);

    Optional<GroupSpecialPermissionGroup> findByGroupIdAndPermissiongroupId(long groupId, long permissiongroupId);


}
