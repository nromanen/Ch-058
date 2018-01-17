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

@Service
public class SocialService {
	public static final int MAX_NAME_LENGTH = 16;
	public static final int MIN_NAME_LENGTH = 1;
	public static final int MAX_SURNAME_LENGTH = 32;
	public static final int MIN_SURNAME_LENGTH = 1;

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
            user.setLogin("facebook" + RandomStringUtils.randomAlphabetic(8));
            user.setPassword(UUID.randomUUID().toString());
            String name  = userProfile.getFirstName();
            String surname = userProfile.getLastName();
            SocialService.validateName(user, name);
            SocialService.validateSurname(user, surname);
        } else{
            if(user.getName().contains("Social")){
                user.setName(userProfile.getFirstName());
            }
            if (user.getSurname().contains("Social")){
                user.setSurname(userProfile.getLastName());
            }
        }
        return user;
    }

    public User googleProfileExtract(Connection<Google> connection){
        Person person = connection.getApi().plusOperations().getGoogleProfile();
        User user = repository.getByEmail(person.getAccountEmail());
        if(user == null){
            user = new User();
            user.setEmail(person.getAccountEmail());
            user.setLogin("google" + RandomStringUtils.randomAlphabetic(8));
            user.setPassword(UUID.randomUUID().toString());
            String name = person.getGivenName();
            String surname = person.getFamilyName();
            SocialService.validateName(user, name);
            SocialService.validateSurname(user, surname);
        }
        return user;
    }

    private static void validateName(User user, String name){
        if((name.length() > MIN_NAME_LENGTH && name.length() < MAX_NAME_LENGTH) && name.matches(NAME_PATTERN)){
            user.setName(name);
        } else{
            user.setName("Name");
        }
    }

    private static void validateSurname(User user, String surname){
        if((surname.length() > MIN_SURNAME_LENGTH && surname.length() < MAX_SURNAME_LENGTH) && surname.matches(NAME_PATTERN)){
            user.setSurname(surname);
        } else{
            user.setSurname("Surname");
        }
    }


}
