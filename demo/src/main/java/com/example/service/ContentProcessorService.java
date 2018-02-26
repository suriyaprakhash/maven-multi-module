package com.example.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.processor.ContentProcessor;

/**
 * This content processor service calls the Processor for processing the input
 * data
 * 
 * @version 1.0
 *
 */
@Service
public class ContentProcessorService {
	/**
	 * This method reads the byte from multipart and puts them into a list of string
	 * and calls the procesor to process the sting list input
	 * 
	 * @param inputfile
	 * @return
	 * @throws GtogException
	 */
	public String handleValidInput(MultipartFile inputfile) throws GtogException {
		InputStream is;
		BufferedReader bfReader;
		String answer = GtogConstants.EMPTY_STRING;
		List<String> inputLines = new ArrayList<String>();
		try {
			byte[] bytes = inputfile.getBytes();
			is = new ByteArrayInputStream(bytes);
			bfReader = new BufferedReader(new InputStreamReader(is));
			String inputLine = null;
			while ((inputLine = bfReader.readLine()) != null) {
				inputLines.add(inputLine);
			}
			//calls the processor
			answer = ContentProcessor.processData(inputLines);
		} catch (IOException e) {
			throw new GtogException("input file exception", e);
		}
		return answer;
	}

}
