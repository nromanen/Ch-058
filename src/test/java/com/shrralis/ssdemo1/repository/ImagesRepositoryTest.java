package com.shrralis.ssdemo1.repository;

import com.shrralis.ssdemo1.configuration.DatabaseConfig;
import com.shrralis.ssdemo1.entity.Image;
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
@ContextConfiguration(classes = {DatabaseConfig.class})
@WebAppConfiguration
@Transactional
public class ImagesRepositoryTest extends TestCase {

	@Autowired
	private ImagesRepository repository;

	private Image testImage;

	@Before
	public void setUp() throws Exception {

		testImage = new Image();
		testImage.setSrc("test/src");
		testImage.setHash("testHash");
		testImage.setType(Image.Type.ISSUE);
		repository.save(testImage);
	}

	@Test
	public void testGetByHash() throws Exception{

		Image image = repository.getByHash(testImage.getHash());
		assertNotNull(image);
		assertEquals(image.getHash(), testImage.getHash());
	}
}
