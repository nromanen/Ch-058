package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.User;

import java.util.List;

/**
 * Interface for getting user accounts information.
 *
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0 Created 12/24/17 at 1:29 AM
 */
public interface IUserService {

	List<User> getAllUsers();

	User getUser(int id);

    User findById(Integer id);

    User findByLogin(String login);

    List<User> findByLoginOrEmailContainingAllIgnoreCase(String login, String email);

    List<User> findAll();

	User setStatus(User.Type userType, Integer id);

    List<User> findByType(User.Type type);
}
