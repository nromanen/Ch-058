package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.plus.Person;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.shrralis.ssdemo1.entity.User.*;

@Service
public class SocialService {

	private final UsersRepository repository;

	@Autowired
	public SocialService(UsersRepository repository) {
		this.repository = repository;
	}

	public static final String NAME_PATTERN = "^[A-ZА-ЯІЇЄ]['a-zа-яіїє]+$";

    public User facebookProfileExtract(Connection<Facebook> connection){
        UserProfile userProfile = connection.fetchUserProfile();
        User user = repository.getByEmail(userProfile.getEmail());
        if (user == null) {
            user = new User();
            user.setEmail(userProfile.getEmail());
	        SocialService.findLogin(user);
	        user.setPassword(UUID.randomUUID().toString());
            String name  = userProfile.getFirstName();
            SocialService.validateAndSetName(user, name);
	        String surname = userProfile.getLastName();
	        SocialService.validateAndSetSurname(user, surname);
        }
        return user;
    }

    public User googleProfileExtract(Connection<Google> connection){
        Person person = connection.getApi().plusOperations().getGoogleProfile();
        User user = repository.getByEmail(person.getAccountEmail());
        if(user == null){
            user = new User();
            user.setEmail(person.getAccountEmail());
            SocialService.findLogin(user);
            user.setPassword(UUID.randomUUID().toString());
            String name = person.getGivenName();
            SocialService.validateAndSetName(user, name);
            String surname = person.getFamilyName();
	        SocialService.validateAndSetSurname(user, surname);
        }
        return user;
    }

    private static void validateAndSetName(User user, String name){
        if((name.length() > MIN_NAME_LENGTH && name.length() < MAX_NAME_LENGTH) && name.matches(NAME_PATTERN)){
            user.setName(name);
        } else{
            user.setName(null);
        }
    }

    private static void validateAndSetSurname(User user, String surname){
        if((surname.length() > MIN_NAME_LENGTH && surname.length() < MAX_NAME_LENGTH) && surname.matches(NAME_PATTERN)){
            user.setSurname(surname);
        } else{
            user.setSurname(null);
        }
    }

    private static void findLogin(User user){
    	user.setLogin(user.getEmail().replace("@", ""));
    	String login = user.getLogin();
    	if (login.length() > 16){
    		user.setLogin(login.substring(0, 16));
	    }
    }


}
