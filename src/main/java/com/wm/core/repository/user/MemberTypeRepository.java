package com.wm.core.repository.user;

import com.wm.core.model.user.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MemberTypeRepository extends JpaRepository<MemberType, Long> {
	Optional<MemberType> findByName(String name);
}
