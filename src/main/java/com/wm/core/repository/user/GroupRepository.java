package com.wm.core.repository.user;

import com.wm.core.model.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Optional<Group> findByName(String name);


	List<Group> findByGrouptypeId(long grouptypeId);

}
