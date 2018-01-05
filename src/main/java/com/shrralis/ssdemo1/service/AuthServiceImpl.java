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

import com.shrralis.ssdemo1.dto.PasswordRecoveryDTO;
import com.shrralis.ssdemo1.dto.RegisterUserDTO;
import com.shrralis.ssdemo1.dto.RegisteredUserDTO;
import com.shrralis.ssdemo1.dto.UserSessionDTO;
import com.shrralis.ssdemo1.dto.mapper.RegisteredUserMapper;
import com.shrralis.ssdemo1.entity.RecoveryToken;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.exception.*;
import com.shrralis.ssdemo1.mail.PasswordRecoveryEmailMessage;
import com.shrralis.ssdemo1.mail.interfaces.IMailCitizenService;
import com.shrralis.ssdemo1.repository.RecoveryTokensRepository;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.security.exception.TooManyNonExpiredRecoveryTokensException;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import com.shrralis.ssdemo1.service.interfaces.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

/**
 * @author shrralis (https://t.me/Shrralis)
 * @version 1.0
 * Created 12/21/17 at 5:26 PM
 */
@Service
@Transactional
public class AuthServiceImpl implements IAuthService {

	private UsersRepository repository;
	private RecoveryTokensRepository tokensRepository;
	private PasswordEncoder passwordEncoder;
	private RegisteredUserMapper userToRegisteredUserDto;
	private IMailCitizenService mailService;
	private AuthenticationTrustResolver authTrustResolver;

	@Autowired
	public AuthServiceImpl(
			UsersRepository repository,
			RecoveryTokensRepository tokensRepository,
			PasswordEncoder passwordEncoder,
			RegisteredUserMapper userToRegisteredUserDto,
			IMailCitizenService mailService,
			AuthenticationTrustResolver authTrustResolver) {
		this.repository = repository;
		this.tokensRepository = tokensRepository;
		this.passwordEncoder = passwordEncoder;
		this.userToRegisteredUserDto = userToRegisteredUserDto;
		this.mailService = mailService;
		this.authTrustResolver = authTrustResolver;
	}

	@Override
	public String generateRecoveryToken(final String login, final String ip)
			throws AbstractCitizenException, MessagingException {
		final User user = repository.getByLogin(login);

		if (user == null) {
			throw new EntityNotExistException(EntityNotExistException.Entity.USER, "login");
		}

		if (tokensRepository.countNonExpiredByUser(user.getId()) > 2) {
			throw new TooManyNonExpiredRecoveryTokensException(user.getLogin());
		}

		final RecoveryToken token = RecoveryToken.Builder.aRecoveryToken()
				.setUser(user)
				.setToken(DigestUtils.md5DigestAsHex((login + ip + LocalDateTime.now().toString()).getBytes()))
				.build();

		tokensRepository.save(token);
		mailService.send(PasswordRecoveryEmailMessage.Builder.aPasswordRecoveryEmailMessage()
				.setDestEmail(user.getEmail())
				.setMessage(token.getToken(), user.getLogin(), ip)
				.build());
		return login;
	}

	@Override
	public UserSessionDTO getCurrentSession() {
		final AuthorizedUser authorizedUser = AuthorizedUser.getCurrent();

		if (isCurrentAuthenticationAnonymous(
				SecurityContextHolder.getContext().getAuthentication(), authTrustResolver)
				|| authorizedUser == null) {
			return new UserSessionDTO(false);
		}
		return new UserSessionDTO(
				authorizedUser.getId(),
				authorizedUser.getUsername(),
				authorizedUser.getType());
	}

	@Override
	public RegisteredUserDTO recoverPassword(final PasswordRecoveryDTO dto) throws AbstractCitizenException {
		final RecoveryToken token = tokensRepository.getByToken(dto.getToken());
		final User user = repository.getByLogin(dto.getLogin());

		checkDataForPasswordRecovering(token, user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setFailedAuthCount(0);
		repository.save(user);
		tokensRepository.delete(token);
		return userToRegisteredUserDto.userToRegisteredUserDto(user);
	}

	@Override
	public RegisteredUserDTO signUp(final RegisterUserDTO user) throws AbstractCitizenException {
		if (repository.getByLogin(user.getLogin()) != null) {
			throw new EntityNotUniqueException(EntityNotUniqueException.Entity.USER, "login");
		}

		if (repository.getByEmail(user.getEmail()) != null) {
			throw new EntityNotUniqueException(EntityNotUniqueException.Entity.USER, "email");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		final User savedUser = repository.save(User.Builder.anUser()
				.setLogin(user.getLogin())
				.setEmail(user.getEmail())
				.setPassword(user.getPassword())
				.setName(user.getName())
				.setSurname(user.getSurname())
				.build());

		return userToRegisteredUserDto.userToRegisteredUserDto(savedUser);
	}

	private boolean isCurrentAuthenticationAnonymous(final Authentication auth,
	                                                 final AuthenticationTrustResolver authTrustResolver) {
		return authTrustResolver.isAnonymous(auth);
	}

	private void checkDataForPasswordRecovering(RecoveryToken token, User user)
			throws AbstractCitizenException {
		if (token == null) {
			throw new EntityNotExistException(EntityNotExistException.Entity.RECOVERY_TOKEN);
		}

		if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new ExpiredRecoveryTokenException();
		}

		if (user == null) {
			throw new EntityNotExistException(EntityNotExistException.Entity.USER, "login");
		}

		if (!user.getId().equals(token.getUser().getId())) {
			throw new IllegalParameterException("login");
		}
	}
}
