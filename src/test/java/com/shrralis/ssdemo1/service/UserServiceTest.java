package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.configuration.AppConfig;
import com.shrralis.ssdemo1.entity.User;
import com.shrralis.ssdemo1.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class UserServiceTest {

	@Mock
	private UsersRepository repository;

	@InjectMocks
	private UserServiceImpl service;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUser() throws Exception {

		User testUser = User.Builder.anUser()
				.setLogin("testLogin")
				.setEmail("testEmail")
				.setPassword("testPassword")
				.setName("testName")
				.setSurname("testSurname")
				.setImage(null)
				.setType(User.Type.USER)
				.setFailedAuthCount(0)
				.build();
		when(repository.getOne(1)).thenReturn(testUser);
		User user = service.getUser(1);
		assertNotNull(user);
		assertEquals(user.getLogin(), testUser.getLogin());
	}
}
