package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.configuration.DatabaseConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfig.class})
@WebAppConfiguration
@Transactional
public class NotificationRepositoryTest extends TestCase {

	@Autowired
	private NotificationRepository repository;

	@Test
	public void testExistsByIssueIdAndUserId() throws Exception {

		long nonexistentUserId = -1L;
		long nonexistentIssueId = -1L;
		boolean result = repository.existsByIssueIdAndUserId(nonexistentIssueId, nonexistentUserId);
		assertTrue(result == false);
	}
}
