package com.wm.core.repository.user;

import com.wm.core.model.user.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleTypesRepository extends JpaRepository<RoleTypes, Long> {
	Optional<RoleTypes> findByName(String name);
}
