package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.entity.UserConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<UserConnection, String> {
	UserConnection getByUserId(String userId);
}
