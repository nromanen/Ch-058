package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.configuration.TestDatabaseConfig;
import com.shrralis.ssdemo1.entity.MapMarker;
import junit.framework.TestCase;
import org.junit.Before;
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
public class MapMarkersRepositoryTest extends TestCase {

	@Autowired
	private MapMarkersRepository repository;

	private MapMarker testMarker;

	@Before
	public void setUp() throws Exception {

		testMarker = new MapMarker();
		testMarker.setLat(1.23456);
		testMarker.setLng(6.54321);
		repository.save(testMarker);
	}

	@Test
	public void testGetByLatAndLng() throws Exception {

		MapMarker marker = repository.getByLatAndLng(testMarker.getLat(), testMarker.getLng());
		assertNotNull(marker);
	}

}
