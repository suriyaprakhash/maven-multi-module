package com.example.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.utils.GtogUtil;

@RunWith(MockitoJUnitRunner.class)
public class GtogUtilTest {
	private static final Logger logger = LogManager.getLogger(GtogUtil.class);

	List<DirectData> directDataList;
	StringBuilder galacticDataQuestionBuilder = null;

	@Before
	public void setUp() {
		directDataList = new ArrayList();
		DirectData directData1 = new DirectData("I", "abc", 1);
		DirectData directData2 = new DirectData("V", "def", 5);
		DirectData directData3 = new DirectData("X", "ghi", 10);
		DirectData directData4 = new DirectData("L", "jkl", 50);
		DirectData directData5 = new DirectData("C", "mno", 100);
		DirectData directData6 = new DirectData("D", "pqr", 500);
		DirectData directData7 = new DirectData("M", "stv", 1000);
		directDataList.add(directData1);
		directDataList.add(directData2);
		directDataList.add(directData3);
		directDataList.add(directData4);
		directDataList.add(directData5);
		directDataList.add(directData6);
		directDataList.add(directData7);

		galacticDataQuestionBuilder = new StringBuilder("");
	}

	// Test cases to test galactic to decimal
	
	//Valid case
	@Test
	public void galacticToDecimalNormal1Test() throws GtogException {
		String[] individualQuestionData = { "ghi", "jkl", "abc" }; // expect 41
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
		assertEquals(41, (int) total);
	}

	//Valid case
	@Test
	public void galacticToDecimalNormal2Test() throws GtogException {
		String[] individualQuestionData = { "stv", "mno", "stv", "ghi", "jkl", "abc", "def" }; // expect 1944
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
		assertEquals(1944, (int) total);
	}

	//Valid case
	@Test
	public void galacticToDecimalNormal3Test() throws GtogException {
		String[] individualQuestionData = { "stv", "pqr", "mno", "mno", "mno", "ghi", "mno", "abc" }; // expect 1891
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
		assertEquals(1891, (int) total);
	}

	// This test case doen not validate the actual roman numeral
	@Test
	public void galacticToDecimalNormal4Test() throws GtogException {
		String[] individualQuestionData = { "abc", "abc", "abc", "def" }; // expect 6 - but not actual roman
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
		assertEquals(6, (int) total);
	}

	// repeate test for 1,10,100,1000
	@Test(expected = GtogException.class)
	public void galacticToDecimalErrorCheckRepeat1Test() throws GtogException {
		String[] individualQuestionData = { "abc", "abc", "abc", "abc" };
		GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData, directDataList,
				galacticDataQuestionBuilder);
	}

	// repeate test for 5,50,500
	@Test(expected = GtogException.class)
	public void galacticToDecimalErrorCheckRepeat2Test() throws GtogException {
		String[] individualQuestionData = { "pqr", "pqr", "mno", "abc" };
		GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData, directDataList,
				galacticDataQuestionBuilder);
	}

	// check if subtract 1 from 1000
	@Test(expected = GtogException.class)
	public void galacticToDecimalErrorCheckSubtraction1Test() throws GtogException {
		String[] individualQuestionData = { "abc", "stv" };
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
	}

	// check if subtract 5 from 1000
	@Test(expected = GtogException.class)
	public void galacticToDecimalErrorCheckSubtraction2Test() throws GtogException {
		String[] individualQuestionData = { "def", "stv" };
		double total = GtogUtil.galacticToDecimal(0, individualQuestionData.length, individualQuestionData,
				directDataList, galacticDataQuestionBuilder);
		logger.info(total);
		logger.info(galacticDataQuestionBuilder);
	}

}
