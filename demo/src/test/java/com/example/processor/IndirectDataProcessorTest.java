package com.example.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.model.DirectData;
import com.example.model.IndirectData;

@RunWith(MockitoJUnitRunner.class)
public class IndirectDataProcessorTest {


	// valid
	@Test
	public void processIndirectDataTest() {
		List<DirectData> directDataList = new ArrayList();
		List<IndirectData> indirectDataList = new ArrayList();
		List<String> directDataInputLineList = new ArrayList();
		List<String> indirectDataInputLineList= new ArrayList();
		
		directDataInputLineList.add("glob is I");
		indirectDataInputLineList.add("glob glob Silver is 34 Credits");
		DirectDataProcessor.processDirectData(directDataInputLineList, directDataList);
		IndirectDataProcessor.processIndirectData(indirectDataInputLineList, indirectDataList, directDataList);
		assertEquals(17, (int) indirectDataList.get(0).getMetalValue());
	}

	// Indirect data tests
	// c > 3 times
	@Test
	public void invalidIndirectData1Test() {
		List<DirectData> directDataList = new ArrayList();
		List<IndirectData> indirectDataList = new ArrayList();
		List<String> directDataInputLineList = new ArrayList();
		List<String> indirectDataInputLineList= new ArrayList();
		
		directDataInputLineList.add("glob is C");
		indirectDataInputLineList.add("glob glob glob glob Silver is 34 Credits");

		DirectDataProcessor.processDirectData(directDataInputLineList, directDataList);
		IndirectDataProcessor.processIndirectData(indirectDataInputLineList, indirectDataList, directDataList);
		assertEquals(0,  indirectDataList.size());
	}

	// L > 1 times
	@Test
	public void invalidIndirectData2Test() {
		List<DirectData> directDataList = new ArrayList();
		List<IndirectData> indirectDataList = new ArrayList();
		List<String> directDataInputLineList = new ArrayList();
		List<String> indirectDataInputLineList= new ArrayList();
		
		directDataInputLineList.add("glob is L");
		indirectDataInputLineList.add("glob glob Silver is 34 Credits");

		DirectDataProcessor.processDirectData(directDataInputLineList, directDataList);
		IndirectDataProcessor.processIndirectData(indirectDataInputLineList, indirectDataList, directDataList);
		assertEquals(0,  indirectDataList.size());
	}

	// no metal or expect before is
	@Test
	public void invalidIndirectData3Test() {
		
		List<DirectData> directDataList = new ArrayList();
		List<IndirectData> indirectDataList = new ArrayList();
		List<String> directDataInputLineList = new ArrayList();
		List<String> indirectDataInputLineList= new ArrayList();
		
		directDataInputLineList.add("glob is L");
		indirectDataInputLineList.add("glob timber glob is 34 Credits");

		DirectDataProcessor.processDirectData(directDataInputLineList, directDataList);
		IndirectDataProcessor.processIndirectData(indirectDataInputLineList, indirectDataList, directDataList);
		assertEquals(0,  indirectDataList.size());
	}

	// no credit
	@Test
	public void invalidIndirectData4Test() {
		
		List<DirectData> directDataList = new ArrayList();
		List<IndirectData> indirectDataList = new ArrayList();
		List<String> directDataInputLineList = new ArrayList();
		List<String> indirectDataInputLineList= new ArrayList();
		
		directDataInputLineList.add("glob is L");
		indirectDataInputLineList.add("glob timber glob is Credits");

		DirectDataProcessor.processDirectData(directDataInputLineList, directDataList);
		IndirectDataProcessor.processIndirectData(indirectDataInputLineList, indirectDataList, directDataList);
		assertEquals(0,  indirectDataList.size());
	}
}
