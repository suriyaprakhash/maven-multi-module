package com.example.processor;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.Result;

@RunWith(MockitoJUnitRunner.class)
public class DirectQuestionProcessorTest {
	
	@Test
	public void processDirectDataTest() throws GtogException {
		List<String> directQuestionInputLine= new ArrayList();
		List<Result> resultList=new ArrayList();
		List<DirectData> directDataList=new ArrayList();

		directQuestionInputLine.add("how many Credits is glob glob ?");

		DirectData directData=new DirectData();
		directData.setGalactic("glob");
		directData.setNumber(1);
		directData.setRoman("I");
		directDataList.add(directData);
		
		DirectQuestionProcessor.processDirectQuestion(directQuestionInputLine, directDataList, resultList);
		assertEquals("I have no idea what you are talking about", resultList.get(0).getAnswer());
		
	}
	

}
