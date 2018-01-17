/*
 * The following code have been created by Yaroslav Zhyravov (shrralis).
 * The code can be used in non-commercial way for everyone.
 * But for any commercial way it needs a author's agreement.
 * Please contact the author for that:
 *  - https://t.me/Shrralis
 *  - https://twitter.com/Shrralis
 *  - shrralis@gmail.com
 *
 * Copyright (c) 2017 by shrralis (Yaroslav Zhyravov).
 */

package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);

	Optional<User> findByLogin(String login);

	User getByEmail(String email);

	User getByLogin(String login);

	Optional<User> findById(int id);

	List<User> findByLoginOrEmailContainingAllIgnoreCase(String login, String email);

	@Modifying
	@Query("UPDATE User u SET u.type = ?1 WHERE u.id = ?2")
	void setStatus(User.Type userType, Integer id);

	List<User> findByType(User.Type type);
}
