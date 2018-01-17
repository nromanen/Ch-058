package com.shrralis.ssdemo1.service.interfaces;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;

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

    User findById(Integer id) throws AbstractCitizenException;

    User findByLogin(String login) throws AbstractCitizenException;

    List<User> findByLoginOrEmailContaining(String login, String email);

    List<User> findAll();

	User setStatus(User.Type userType, Integer id) throws AbstractCitizenException;

    List<User> findByType(User.Type type);
}
