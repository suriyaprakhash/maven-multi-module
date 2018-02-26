package com.example.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContentClassifierTest {
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

	@Test
	public void classifyInputDirectDataTest() {
		String inputLine = "glob is I";
		List<String> directDataInputLine = new ArrayList();
		List<String> indirectDataInputLine = new ArrayList();
		List<String> directQuestionInputLine = new ArrayList();
		List<String> indirectQuestionInputLine = new ArrayList();
		List<String> irrelevantQuestionInputLine = new ArrayList();
		ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine, directQuestionInputLine,
				indirectQuestionInputLine, irrelevantQuestionInputLine);
		assertEquals("glob is I", directDataInputLine.get(0));
	}

	@Test
	public void classifyInputIndirectDataTest() {
		String inputLine = "glob glob Silver is 34 Credits";
		List<String> directDataInputLine = new ArrayList();
		List<String> indirectDataInputLine = new ArrayList();
		List<String> directQuestionInputLine = new ArrayList();
		List<String> indirectQuestionInputLine = new ArrayList();
		List<String> irrelevantQuestionInputLine = new ArrayList();
		ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine, directQuestionInputLine,
				indirectQuestionInputLine, irrelevantQuestionInputLine);
		assertEquals("glob glob Silver is 34 Credits", indirectDataInputLine.get(0));
	}

	@Test
	public void classifyInputDirectQuestionTest() {
		String inputLine = "how much is pish tegj glob glob ?";
		List<String> directDataInputLine = new ArrayList();
		List<String> indirectDataInputLine = new ArrayList();
		List<String> directQuestionInputLine = new ArrayList();
		List<String> indirectQuestionInputLine = new ArrayList();
		List<String> irrelevantQuestionInputLine = new ArrayList();
		ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine, directQuestionInputLine,
				indirectQuestionInputLine, irrelevantQuestionInputLine);
		assertEquals("how much is pish tegj glob glob ?", directQuestionInputLine.get(0));
	}

	@Test
	public void classifyInputIndirectQuestionTest() {
		String inputLine = "how many Credits is glob prok Iron ?";
		List<String> directDataInputLine = new ArrayList();
		List<String> indirectDataInputLine = new ArrayList();
		List<String> directQuestionInputLine = new ArrayList();
		List<String> indirectQuestionInputLine = new ArrayList();
		List<String> irrelevantQuestionInputLine = new ArrayList();
		ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine, directQuestionInputLine,
				indirectQuestionInputLine, irrelevantQuestionInputLine);
		assertEquals("how many Credits is glob prok Iron ?", indirectQuestionInputLine.get(0));
	}

	@Test
	public void classifyIrreleventQuestionTest() {
		String inputLine = "how much woodchuck chuck if a woodchuck could chuck wood?";
		List<String> directDataInputLine = new ArrayList();
		List<String> indirectDataInputLine = new ArrayList();
		List<String> directQuestionInputLine = new ArrayList();
		List<String> indirectQuestionInputLine = new ArrayList();
		List<String> irrelevantQuestionInputLine = new ArrayList();
		ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine, directQuestionInputLine,
				indirectQuestionInputLine, irrelevantQuestionInputLine);
		assertEquals("how much woodchuck chuck if a woodchuck could chuck wood?", irrelevantQuestionInputLine.get(0));
	}

}
