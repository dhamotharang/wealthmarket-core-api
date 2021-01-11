package com.wm.core.repository.user;

import com.wm.core.model.user.MemberOrganizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberOrganizationsRepository extends JpaRepository<MemberOrganizations, Long> {

    Optional<MemberOrganizations> findByOrganizationIdAndUserId(long organizationId, long userId);

    List<MemberOrganizations> findByOrganizationIdAndStatusId(long organizationId, long statusId);

    List<MemberOrganizations> findByOrganizationId(long organizationId);

    List<MemberOrganizations> findByUserIdAndStatusId(long userId, long statusId);

    Optional<MemberOrganizations> findByUserId(long userId);

    List<MemberOrganizations> findByRoleId(long roleId);
}
