package com.shrralis.ssdemo1.service;

import com.shrralis.ssdemo1.configuration.AppConfig;
import com.shrralis.ssdemo1.dto.MarkerDTO;
import com.shrralis.ssdemo1.entity.MapMarker;
import com.shrralis.ssdemo1.repository.MapMarkersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class MapMarkerServiceTest {

	@Mock
	private MapMarkersRepository repository;

	@InjectMocks
	private MapMarkersServiceImpl service;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetMarker() throws Exception {

		MapMarker testMarker = new MapMarker();
		testMarker.setId(1);
		testMarker.setLat(1.0);
		testMarker.setLng(2.0);
		when(repository.getByLatAndLng(1.0, 2.0)).thenReturn(testMarker);
		MapMarker result = service.getMarker(1.0, 2.0);
		assertNotNull(result);
	}
}
