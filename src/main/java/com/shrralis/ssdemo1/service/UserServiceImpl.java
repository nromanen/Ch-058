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
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	public User findById(Integer id) throws AbstractCitizenException {
		return repository.findById(id).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	public User findByLogin(String login) throws AbstractCitizenException {
		return repository.findByLogin(login).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	public Page<User> findByLoginOrEmail(String login, String email, Pageable pageable) {
		return repository.findByLoginContainingOrEmailContainingAllIgnoreCase(login, email, pageable);
	}

	@Override
	public User setStatus(User.Type type, Integer id) {
		repository.setStatus(type, id);
		return repository.getOne(id);
	}

	@Override
	public Page<User> findByType(User.Type type, Pageable pageable) {
		return repository.findByType(type, pageable);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

//	@Override
//	public Page<User> findAll(Predicate predicate, Pageable pageable) {
//		return repository.findAll(predicate, pageable);
//	}
}
