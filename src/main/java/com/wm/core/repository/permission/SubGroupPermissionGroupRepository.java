package com.wm.core.repository.permission;

import com.wm.core.model.permission.SubGroupPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubGroupPermissionGroupRepository extends JpaRepository<SubGroupPermissionGroup, Long> {

    List<SubGroupPermissionGroup> findBySubgroupId(long subgroupId);

    Optional<SubGroupPermissionGroup> findBySubgroupIdAndPermissiongroupId(long subgroupId, long permissiongroupId);
}
