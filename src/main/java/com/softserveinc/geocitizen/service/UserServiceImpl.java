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

package com.softserveinc.geocitizen.service;

import com.softserveinc.geocitizen.dto.EditUserDTO;
import com.softserveinc.geocitizen.dto.UserProfileDTO;
import com.softserveinc.geocitizen.entity.Image;
import com.softserveinc.geocitizen.entity.User;
import com.softserveinc.geocitizen.exception.AbstractCitizenException;
import com.softserveinc.geocitizen.exception.EntityNotExistException;
import com.softserveinc.geocitizen.repository.ConnectionRepository;
import com.softserveinc.geocitizen.repository.UsersRepository;
import com.softserveinc.geocitizen.security.model.AuthorizedUser;
import com.softserveinc.geocitizen.service.interfaces.IUserService;
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

	private final UsersRepository repository;
	private final ConnectionRepository connectionRepository;

	@Autowired
	public UserServiceImpl(UsersRepository repository, ConnectionRepository connectionRepository) {
		this.repository = repository;
		this.connectionRepository = connectionRepository;
	}

	@Override
	@ReadOnlyProperty
	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Override
	@ReadOnlyProperty
	public User getUser(int id) {
		return repository.getOne(id);
	}

	@Override
	@ReadOnlyProperty
	public User findById(Integer id) throws AbstractCitizenException {
		return repository.findById(id).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	@ReadOnlyProperty
	public User findByLogin(String login) throws AbstractCitizenException {
		return repository.findByLogin(login).orElseThrow(() -> new EntityNotExistException(EntityNotExistException.Entity.USER));
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findByLoginOrEmailOrNameOrSurname(String login, String email, String name, String surname, Pageable pageable) {
		return repository.findByLoginContainingOrEmailContainingOrNameContainingOrSurnameContainingAllIgnoreCase(login, email, name, surname, pageable);
	}

	@Override
	public User setStatus(User.Type type, Integer id) {
		repository.setStatus(type, id);
		return repository.getOne(id);
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findByType(User.Type type, Pageable pageable) {
		return repository.findByType(type, pageable);
	}

	@Override
	@ReadOnlyProperty
	public Page<User> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public void edit(final EditUserDTO dto) {
		final User user = repository.findOne(AuthorizedUser.getCurrent().getId());

		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		repository.save(user);
	}

	@Override
	public void updateImage(final Image image) {
		final User user = repository.getOne(AuthorizedUser.getCurrent().getId());

		user.setImage(image);
		repository.save(user);
	}

	@Override
	public UserProfileDTO getUserProfile(int id) {
		UserProfileDTO dto = new UserProfileDTO();
		User user = repository.findById(id).get();
		dto.setId(id);
		dto.setLogin(user.getLogin());
		dto.setEmail(user.getEmail());
		dto.setType(user.getType());
		dto.setImage(user.getImage());
		dto.setName(user.getName());
		dto.setSurname(user.getSurname());
		if (connectionRepository.getByUserIdAndProvider(String.valueOf(id), "facebook") != null) {
			dto.setFacebookConnected(true);
		}
		if (connectionRepository.getByUserIdAndProvider(String.valueOf(id), "google") != null) {
			dto.setGoogleConnected(true);
		}
		return dto;
	}
}
