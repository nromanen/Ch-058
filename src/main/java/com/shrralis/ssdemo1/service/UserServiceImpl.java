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

package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	private final UsersRepository repository;

	@Autowired
	public UserServiceImpl(UsersRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Override
	public User getUser(int id) {
		return repository.getOne(id);
	}

	@Override
	public User findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new NullPointerException());
	}

	@Override
	public User findByLogin(String login) {
		return repository.findByLogin(login).orElseThrow(() -> new NullPointerException());
	}

	@Override
	public List<User> findByLoginOrEmailContainingAllIgnoreCase(String login, String email) {
		List<User> res = repository.findByLoginOrEmailContainingAllIgnoreCase(login, email);
		if (res.isEmpty()) {
			throw new NullPointerException();
		}
		return res;
	}

	@Override
	public List<User> findAll() {
		List<User> res = repository.findAll();
		if (res.isEmpty()) {
			throw new NullPointerException();
		}
		return res;
	}

	@Override
	@Transactional
	public User setStatus(User.Type type, Integer id) {
		repository.setStatus(type, id);
		return repository.getOne(id);
	}

	@Override
	public List<User> findByType(User.Type type) {
		return repository.findByType(type);
	}
}
