package com.wm.core.repository.permission;

import com.wm.core.model.permission.UserSpecialPermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSpecialPermissionGroupRepository extends JpaRepository<UserSpecialPermissionGroup, Long> {


    List<UserSpecialPermissionGroup> findByUserId(long userId);

    Optional<UserSpecialPermissionGroup> findByUserIdAndPermissiongroupId(long userId, long permissiongroupId);
}
