package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.security.exception.CitizenBadCredentialsException;
import com.shrralis.ssdemo1.security.model.AuthorizedUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.shrralis.ssdemo1.entity.User.MAX_NAME_LENGTH;
import static com.shrralis.ssdemo1.entity.User.MIN_NAME_LENGTH;

@Service
public class SocialService {

	private final UsersRepository usersRepository;

	@Autowired
	public SocialService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public static final String NAME_PATTERN = "^[A-ZА-ЯІЇЄ]['a-zа-яіїє]+$";

	public User facebookProfileExtract(Connection<Facebook> connection) throws CitizenBadCredentialsException{
		User user;
		UserProfile userProfile = connection.fetchUserProfile();

		if (AuthorizedUser.getCurrent() != null) {
			if (!StringUtils.contains(AuthorizedUser.getCurrent().getEmail(), userProfile.getEmail())) {
				throw new CitizenBadCredentialsException(userProfile.getEmail());
			}

			user = usersRepository.findById(AuthorizedUser.getCurrent().getId()).get();
		} else {
			user = usersRepository.getByEmail(userProfile.getEmail());
		}

		if (user == null) {
			user = new User();
			user.setEmail(userProfile.getEmail());
			SocialService.createLoginFromEmail(user);
			user.setPassword(UUID.randomUUID().toString());
			String name  = userProfile.getFirstName();
			SocialService.validateAndSetName(user, name);
			String surname = userProfile.getLastName();
			SocialService.validateAndSetSurname(user, surname);
		}
		return user;
	}

	private static void validateAndSetSurname(User user, String surname) {
		if ((surname.length() > MIN_NAME_LENGTH && surname.length() < MAX_NAME_LENGTH) && surname.matches(NAME_PATTERN)) {
			user.setSurname(surname);
		} else {
			user.setSurname(null);
		}
	}

	private static void validateAndSetName(User user, String name) {
		if ((name.length() > MIN_NAME_LENGTH && name.length() < MAX_NAME_LENGTH) && name.matches(NAME_PATTERN)) {
			user.setName(name);
		} else {
			user.setName(null);
		}
	}

	public User googleProfileExtract(Connection<Google> connection) throws CitizenBadCredentialsException {
		User user;
		Person person = connection.getApi().plusOperations().getGoogleProfile();
		if (AuthorizedUser.getCurrent() != null) {
			if (!StringUtils.contains(AuthorizedUser.getCurrent().getEmail(), person.getAccountEmail())) {
				throw new CitizenBadCredentialsException(person.getAccountEmail());
			}

			user = usersRepository.findById(AuthorizedUser.getCurrent().getId()).get();
		} else {
			user = usersRepository.getByEmail(person.getAccountEmail());
		}

		if (user == null) {
			user = new User();
			user.setEmail(person.getAccountEmail());
			SocialService.createLoginFromEmail(user);
			user.setPassword(UUID.randomUUID().toString());
			String name = person.getGivenName();
			SocialService.validateAndSetName(user, name);
			String surname = person.getFamilyName();
			SocialService.validateAndSetSurname(user, surname);
		}
		return user;
	}

	private static void createLoginFromEmail(User user){
		user.setLogin(StringUtils.substring(user.getEmail().replace("@", ""), 0, 16));
	}

}
