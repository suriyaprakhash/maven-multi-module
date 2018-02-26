package com.example.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.exception.GtogException;
import com.example.model.DirectData;

@RunWith(MockitoJUnitRunner.class)
public class DirectDataProcessorTest {

	List<String> directDataInputLine;
	List<String> indirectDataInputLine;
	List<String> directQuestionInputLine;
	List<String> indirectQuestionInputLine;
	List<String> irrelevantQuestionInputLine;

	@Before
	public void setUp() {

		directDataInputLine = new ArrayList();
		indirectDataInputLine = new ArrayList();
		directQuestionInputLine = new ArrayList();
		indirectQuestionInputLine = new ArrayList();
		irrelevantQuestionInputLine = new ArrayList();
	}

	// valid
	@Test
	public void processDirectDataTest() throws GtogException {
		String inputLine = "glob is I";
		directDataInputLine.add(inputLine);
		List<DirectData> directDataList = new ArrayList();
		DirectDataProcessor.processDirectData(directDataInputLine, directDataList);
		assertEquals(1, (int) directDataList.get(0).getNumber());
	}

	// invalid data cases
	// not a roman
	@Test
	public void invalidDirectData1Test() {
		directDataInputLine.add("glob is R");
		List<DirectData> directDataList = new ArrayList();
		DirectDataProcessor.processDirectData(directDataInputLine, directDataList);
		assertEquals(0, (int) directDataList.size());

	}

	// not length is 3
	@Test
	public void invalidDirectData2Test() {
		directDataInputLine.add("dsdis I");
		List<DirectData> directDataList = new ArrayList();
		DirectDataProcessor.processDirectData(directDataInputLine, directDataList);
		assertEquals(0, (int) directDataList.size());
	}

	// no 'is'
	@Test
	public void invalidDirectData3Test() {
		directDataInputLine.add("d4 sdis I");
		List<DirectData> directDataList = new ArrayList();
		DirectDataProcessor.processDirectData(directDataInputLine, directDataList);
		assertEquals(0, (int) directDataList.size());
	}
}
