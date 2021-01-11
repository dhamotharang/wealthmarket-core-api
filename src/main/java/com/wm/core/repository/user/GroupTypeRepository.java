package com.wm.core.repository.user;

import com.wm.core.model.user.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {
	Optional<GroupType> findByName(String name);
}
