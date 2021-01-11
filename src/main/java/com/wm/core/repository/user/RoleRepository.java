package com.wm.core.repository.user;

import com.wm.core.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);

	List<Role> findByRoletypeId(long roletypeId);

//	List<Role> findByOrganizationIdAndRoletypeId(long organizationId, long roletypeId);

	Optional<Role> findByOrganizationIdAndName(long organizationId, String name);

	List<Role> findByOrganizationId(long organizationId);
}
