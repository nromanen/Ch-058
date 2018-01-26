package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.configuration.TestDatabaseConfig;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.entity.UserConnection;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
@WebAppConfiguration
@Transactional
public class ConnectionRepositoryTest extends TestCase {

	@Autowired
	ConnectionRepository repository;

	UserConnection userProvider;

	@Test
	public void testGetByEmail() throws Exception{

		UserConnection userProvider = repository.getByUserId("22");
		System.out.println(userProvider);
	}
}
