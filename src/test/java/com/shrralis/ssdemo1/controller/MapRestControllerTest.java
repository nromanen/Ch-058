package com.shrralis.ssdemo1.controller;

import com.shrralis.ssdemo1.configuration.AppConfig;
import com.shrralis.ssdemo1.configuration.DatabaseConfig;
import com.shrralis.ssdemo1.dto.MarkerDTO;
import com.shrralis.ssdemo1.service.interfaces.IIssueService;
import com.shrralis.ssdemo1.service.interfaces.IMapMarkersService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class MapRestControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	@InjectMocks
	private MapRestController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(this.wac).build();
	}

	@Test
	public void test() throws Exception {

		this.mockMvc.perform(get("/map").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

//	@Test
//	public void testLoadAllMarkers() throws Exception {
//		mockMvc.perform(MockMvcRequestBuilders.get("/map/").accept(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$", hasSize(7)));
//		List<MarkerDTO> markers = Arrays.asList(
//				new MarkerDTO( 1.123456, 6.54321, 1),
//				new MarkerDTO( 9.909999, 8.88888, 1));
//
//		when(markerService.loadAllMarkers()).thenReturn(markers);
//
//		mockMvc.perform(get("/map/"))
//
//
//		verify(markerService, times(1)).loadAllMarkers();
//		verifyNoMoreInteractions(markerService);
//	}
}
