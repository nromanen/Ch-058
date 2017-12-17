package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
