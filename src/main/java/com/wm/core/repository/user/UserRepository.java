package com.wm.core.repository.user;

import com.wm.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.email = ?1 or u.phone_number = ?1")
	Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

	@Query("select u from User u where u.email = ?1 or u.username = ?1")
	Optional<User> findByEmailOrUsername(String email, String username);

	Optional<User> findByUsername(String username);

	@Query("select u from User u where u.email like %?1% or u.phone_number like %?2%")
	List<User> searchByEmailOrPhoneNumber(String email, String phoneNumber);

	@Query("select u from User u where concat(u.first_name, u.last_name, u.email, u.phone_number, u.username, u.unique_id) like %?1%")
	List<User> searchForUsers(String firstname, String lastname, String email, String phone, String username,String unique_id);

	List<User> findByUsertypeId(long usertypeId);

}
