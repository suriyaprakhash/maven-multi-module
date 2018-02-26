package com.example.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContentProcessorTest {

	@Before
	public void setUp() {
	}

	// valid test cases
	@Test
	public void valid1Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is I");
		inputLines.add("prok is V");
		inputLines.add("pish is X");
		inputLines.add("tegj is L");
		inputLines.add("glob glob Silver is 34 Credits");
		inputLines.add("glob prok Gold is 57800 Credits");
		inputLines.add("pish pish Iron is 3910 Credits");
		inputLines.add("how much is pish tegj glob glob ?");
		inputLines.add("how many Credits is glob prok Silver ?");
		inputLines.add("how many Credits is glob prok Gold ?");
		inputLines.add("how many Credits is glob prok Iron ?");
		inputLines.add("how much bloc wood blobasdasda woodchuck chuck if a woodchuck could chuck wood ?");
		ContentProcessor.processData(inputLines);
		assertEquals("glob prok Iron is 782", ContentProcessor.getResultList().get(3).getAnswer());
	}

	@Test
	public void valid2Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is I");
		inputLines.add("prok is V");
		inputLines.add("pish is X");
		inputLines.add("tegj is L");
		inputLines.add("mud is C");
		inputLines.add("dinep is D");
		inputLines.add("O is M");
		inputLines.add("glob Silver is 8 Credits");
		inputLines.add("glob pish Gold is 40000 Credits");
		inputLines.add("mud dinep Iron is 873567 Credits");
		inputLines.add("how much is mud O ?");
		inputLines.add("how many Credits is dinep prok Silver ?");
		inputLines.add("how many Credits is O mud Gold ?");
		inputLines.add("how many Credits is pish pish pish tegj Iron ?");
		inputLines.add("how much bloc wood blobasdasda woodchuck chuck if a woodchuck could chuck wood?");
		ContentProcessor.processData(inputLines);
		assertEquals("O mud Gold is 4888888", ContentProcessor.getResultList().get(2).getAnswer());
	}

	// Invalid test scenarios - turn on info for logs

	// Direct data cases
	// not a roman
	@Test
	public void invalidDirectData1Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is R");

		ContentProcessor.processData(inputLines);
	}

	// not length is 3
	@Test
	public void invalidDirectData2Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("dsdis I");

		ContentProcessor.processData(inputLines);
	}

	// no 'is'
	@Test
	public void invalidDirectData3Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("d4 sdis I");

		ContentProcessor.processData(inputLines);
	}

	// Indirect data tests
	// c > 3 times
	@Test
	public void invalidIndirectData1Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is C");
		inputLines.add("glob glob glob glob Silver is 34 Credits");

		ContentProcessor.processData(inputLines);
	}

	// L > 1 times
	@Test
	public void invalidIndirectData2Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is L");
		inputLines.add("glob glob Silver is 34 Credits");

		ContentProcessor.processData(inputLines);
	}

	// no metal or expect before is
	@Test
	public void invalidIndirectData3Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is L");
		inputLines.add("glob timber glob is 34 Credits");

		ContentProcessor.processData(inputLines);
	}

	// no credit
	@Test
	public void invalidIndirectData4Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is L");
		inputLines.add("glob timber glob is Credits");

		ContentProcessor.processData(inputLines);
	}

	// direct questions
	// M > 3 times
	@Test
	public void invalidDirectQuestion1Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is M");
		inputLines.add("glob glob glob Silver is 34540 Credits");
		inputLines.add("how many Credits is glob glob glob glob Silver ?");

		ContentProcessor.processData(inputLines);
	}

	// V > 1 times
	@Test
	public void invalidDirectQuestion2Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is V");
		inputLines.add("glob glob glob Silver is 34540 Credits");
		inputLines.add("how many Credits is glob glob glob glob Silver ?");

		ContentProcessor.processData(inputLines);
	}

	// invalid galactic string
	@Test
	public void invalidDirectQuestion3Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is V");
		inputLines.add("glob glob glob Silver is 34540 Credits");
		inputLines.add("how many Credits is asdas glob Silver ?");

		ContentProcessor.processData(inputLines);
	}

	// invalid metal string
	@Test
	public void invalidDirectQuestion4Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is V");
		inputLines.add("glob glob glob Silver is 34540 Credits");
		inputLines.add("how many Credits is glob ssdSilver ?");

		ContentProcessor.processData(inputLines);
	}

	// C-I not possible
	@Test
	public void invalidDirectQuestion5Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is I");
		inputLines.add("blob is C");
		inputLines.add("glob glob glob Silver is 34540 Credits");
		inputLines.add("how many Credits is glob blob Silver ?");

		ContentProcessor.processData(inputLines);
	}

	// D-M not possible
	@Test
	public void invalidDirectQuestion6Test() {
		List<String> inputLines = new ArrayList<String>();
		inputLines.add("glob is D");
		inputLines.add("blob is M");
		inputLines.add("glob Silver is 34540 Credits");
		inputLines.add("how many Credits is glob blob Silver ?");

		ContentProcessor.processData(inputLines);
	}

}
