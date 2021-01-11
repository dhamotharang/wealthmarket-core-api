package com.wm.core.repository.user;

import com.wm.core.model.user.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {


	Optional<UserVerification> findByCode(String recoverycode);
}
