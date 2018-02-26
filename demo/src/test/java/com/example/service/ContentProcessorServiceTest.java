package com.example.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import com.example.exception.GtogException;

@RunWith(MockitoJUnitRunner.class)
public class ContentProcessorServiceTest {

	@Mock
	private ContentProcessorService contentProcessorService;

	@Before
	public void setUp() {
		contentProcessorService = new ContentProcessorService();
	}

	@Test
	public void handleValidInputTest() throws GtogException {
		MockMultipartFile file = new MockMultipartFile("file.txt", "orig", null,
				"Credits is glob glob Drum?".getBytes());
		assertEquals("I have no idea what you are talking about/n", contentProcessorService.handleValidInput(file));

	}

}
