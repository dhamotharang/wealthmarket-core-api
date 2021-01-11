package com.wm.core.repository.user;

import com.wm.core.model.user.GuestVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestVisitorRepository extends JpaRepository<GuestVisitor, Long> {


    Optional<GuestVisitor> findByIpaddress(String IpAddress);

    List<GuestVisitor> findByUsertypeId(long usertypeId);

}
