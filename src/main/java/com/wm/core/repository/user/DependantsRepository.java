package com.wm.core.repository.user;

import com.wm.core.model.user.Dependants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DependantsRepository extends JpaRepository<Dependants, Long> {


    Optional<Dependants> findByDependantuserId(long userId);

    List<Dependants> findByParentuserId(long userId);

}
