package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.configuration.TestDatabaseConfig;
import com.shrralis.ssdemo1.entity.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDatabaseConfig.class})
@WebAppConfiguration
@Transactional
public class UsersRepositoryTest extends TestCase {

	@Autowired
	private UsersRepository repository;

	private User testUser;

	@Before
	public void setUp() throws Exception {

		testUser = User.Builder.anUser()
				.setLogin("testLogin")
				.setEmail("testEmail")
				.setPassword("testPassword")
				.setName("testName")
				.setSurname("testSurname")
				.setImage(null)
				.setType(User.Type.USER)
				.setFailedAuthCount(0)
				.build();
		repository.save(testUser);
	}

	@Test
	public void testGetByLogin() throws Exception{

		User user = repository.getByLogin(testUser.getLogin());
		assertNotNull(user);
		assertThat(user.getEmail()).isEqualTo(testUser.getEmail());
		assertTrue(user.getFailedAuthCount() == testUser.getFailedAuthCount());
	}

	@Test
	public void testGetByEmail() throws Exception{

		User user = repository.getByEmail(testUser.getEmail());
		assertNotNull(user);
		assertEquals(user.getLogin(), testUser.getLogin());
		assertTrue(user.getFailedAuthCount() == testUser.getFailedAuthCount());
	}
}
