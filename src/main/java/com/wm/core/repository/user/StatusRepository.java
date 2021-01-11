package com.wm.core.repository.user;

import com.wm.core.model.user.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

	Optional<Status> findByName(String name);
}
