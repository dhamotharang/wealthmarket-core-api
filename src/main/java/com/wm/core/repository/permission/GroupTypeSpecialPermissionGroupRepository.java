package com.wm.core.repository.permission;

import com.wm.core.model.permission.GroupTypeSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface GroupTypeSpecialPermissionGroupRepository extends JpaRepository<GroupTypeSpecialPermissionGroup, Long> {


    List<GroupTypeSpecialPermissionGroup> findByGrouptypeId(long grouptypeId);


    Optional<GroupTypeSpecialPermissionGroup> findByGrouptypeIdAndPermissiongroupId(long grouptypeId, long permissiongroupId);
}
