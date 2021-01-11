package com.wm.core.repository.user;

import com.wm.core.model.user.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {

	Optional<UserType> findUserTypeByName(String name);
}
