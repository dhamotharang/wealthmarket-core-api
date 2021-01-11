package com.wm.core.repository.user;

import com.wm.core.model.user.SubGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubGroupRepository extends JpaRepository<SubGroup, Long> {
	Optional<SubGroup> findByName(String name);


	List<SubGroup> findByGroupId(long groupId);

	List<SubGroup> findByGrouptypeId(long grouptypeId);



}
