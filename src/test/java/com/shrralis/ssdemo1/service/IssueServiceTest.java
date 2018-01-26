package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.configuration.AppConfig;
import com.shrralis.ssdemo1.entity.Issue;
import com.shrralis.ssdemo1.repository.IssuesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class IssueServiceTest {

	@Mock
	private IssuesRepository repository;

	@InjectMocks
	private IssueServiceImpl service;

	private List<Issue> testList;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);

		Issue.Type type = new Issue.Type();
		type.setName("PROBLEM");

		Issue testIssue = Issue.Builder.anIssue()
				.setMapMarker(null)
				.setTitle("title")
				.setText("description")
				.setAuthor(null)
				.setImage(null)
				.setType(type)
				.setClosed(false)
				.setCreatedAt(LocalDateTime.now())
				.setUpdatedAt(LocalDateTime.now())
				.build();
		testList = Arrays.asList(testIssue);
	}

	@Test
	public void testGetAllIssueByMapMarker() throws Exception {

		when(repository.findByMapMarker_Id(1)).thenReturn(testList);
		List<Issue> list = service.getAllIssueByMapMarker(1);
		assertNotNull(list);
		assertEquals(list.size(), 1);
	}
}
