package com.wm.core.repository.user;

import com.wm.core.model.user.SubMemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubMemberTypeRepository extends JpaRepository<SubMemberType, Long> {

	List<SubMemberType> findByMembertypeId(long membertypeId);

}
