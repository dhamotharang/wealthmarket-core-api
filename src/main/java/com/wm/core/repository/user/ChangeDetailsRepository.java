package com.wm.core.repository.user;

import com.wm.core.model.user.ChangeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChangeDetailsRepository extends JpaRepository<ChangeDetails, Long> {

	List<ChangeDetails> findByUserId(long userId);
}
