package com.example.controller;

import org.junit.Before;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.processor.ContentProcessor;

public class FileUploadControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Mock
	private ContentProcessor contentProcessor;

	@Mock
	private FileUploadController fileUploadController;

	@Before
	public void setUp() {
		// contentProcessor=new ContentProcessorImpl();
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mockMvc = MockMvcBuilders.standaloneSetup(fileUploadController).build();

	}

}
