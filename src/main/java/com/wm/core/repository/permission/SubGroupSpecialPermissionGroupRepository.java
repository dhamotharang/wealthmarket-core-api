package com.wm.core.repository.permission;

import com.wm.core.model.permission.SubGroupSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubGroupSpecialPermissionGroupRepository extends JpaRepository<SubGroupSpecialPermissionGroup, Long> {

    List<SubGroupSpecialPermissionGroup> findBySubgroupId(long subgroupId);

    Optional<SubGroupSpecialPermissionGroup> findBySubgroupIdAndPermissiongroupId(long subgroupId, long permissiongroupId);
}
