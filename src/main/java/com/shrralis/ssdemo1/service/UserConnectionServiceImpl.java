package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import com.shrralis.ssdemo1.security.exception.CitizenBadCredentialsException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.UUID;

@Service
@Transactional
public class UserConnectionServiceImpl implements ConnectionSignUp{

	private final UsersRepository usersRepository;
	private final SocialService service;

	@Autowired
	public UserConnectionServiceImpl(UsersRepository usersRepository, SocialService service) {
		this.usersRepository = usersRepository;
		this.service = service;
	}

	@Override
	public String execute(Connection<?> connection) throws CitizenBadCredentialsException {


		if(StringUtils.contains(connection.getProfileUrl(), "facebook")){
			return String.valueOf(usersRepository.save(service.facebookProfileExtract((Connection<Facebook>)connection)).getId());
		}

		return String.valueOf(usersRepository.save(service.googleProfileExtract((Connection<Google>)connection)).getId());
	}
}
