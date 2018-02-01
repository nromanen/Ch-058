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

import com.shrralis.ssdemo1.dto.EditUserDTO;
import com.shrralis.ssdemo1.entity.Image;
import com.shrralis.ssdemo1.dto.UserProfileDTO;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.AbstractCitizenException;
import com.shrralis.ssdemo1.exception.EntityNotExistException;
import com.shrralis.ssdemo1.repository.ConnectionRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	private final UsersRepository usersRepository;
	private final ConnectionRepository connectionRepository;

	@Autowired
	public UserServiceImpl(UsersRepository usersRepository, ConnectionRepository connectionRepository) {
		this.usersRepository = usersRepository;
		this.connectionRepository = connectionRepository;
	}

	@Override
	@ReadOnlyProperty
	public List<User> getAllUsers() {
		return usersRepository.findAll();
	}

	@Override
	@ReadOnlyProperty
	public User getUser(int id) {
		return usersRepository.getOne(id);
	}

	@Override
	@ReadOnlyProperty
	public User findById(Integer id) throws AbstractCitizenException {
		return usersRepository.findById(id).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	@ReadOnlyProperty
	public User findByLogin(String login) throws AbstractCitizenException {
		return usersRepository.findByLogin(login).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findByLoginOrEmail(String login, String email, Pageable pageable) {
		return usersRepository.findByLoginContainingOrEmailContainingAllIgnoreCase(login, email, pageable);
	}

	@Override
	public User setStatus(User.Type type, Integer id) {
		usersRepository.setStatus(type, id);
		return usersRepository.getOne(id);
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findByType(User.Type type, Pageable pageable) {
		return usersRepository.findByType(type, pageable);
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findAll(Pageable pageable) {
		return usersRepository.findAll(pageable);
	}

//	@Override
//	public Page<User> findAll(Predicate predicate, Pageable pageable) {
//		return repository.findAll(predicate, pageable);
//	}

	@Override
	public void edit(final EditUserDTO dto) {
		final User user = usersRepository.findOne(AuthorizedUser.getCurrent().getId());

		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		usersRepository.save(user);
	}

	@Override
	public UserProfileDTO getUserProfile(int id) {
		UserProfileDTO dto = new UserProfileDTO();
		User user = usersRepository.findById(id).get();
		dto.setId(id);
		dto.setLogin(user.getLogin());
		dto.setEmail(user.getEmail());
		dto.setType(user.getType());
		dto.setImage(user.getImage());
		dto.setName(user.getName());
		dto.setSurname(user.getSurname());
		if(connectionRepository.getByUserIdAndProvider(String.valueOf(id), "facebook") != null){
			dto.setFacebookConnected(true);
		}
		if(connectionRepository.getByUserIdAndProvider(String.valueOf(id), "google") != null){
			dto.setGoogleConnected(true);
		}
		return dto;
	}

	@Override
	public void updateImage(final Image image) {
		final User user = repository.getOne(AuthorizedUser.getCurrent().getId());

		user.setImage(image);
		repository.save(user);
	}
}
