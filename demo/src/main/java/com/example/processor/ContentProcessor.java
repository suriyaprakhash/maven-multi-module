package com.example.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.constants.GtogConstants;
import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.model.Result;
import com.example.utils.GtogUtil;

/**
 * 
 * This is the Processor class that process the input data. It has the following
 * process, <ui> Setting up initial data</ui><ui> Classify the data</ui> <ui>
 * Processsing </ui> <ui> Extracting the result {@link Result}</ui> to process
 * the <i>answers</i> for the <i>questions</i>
 * 
 * @version 1.0
 *
 */
public class ContentProcessor {

	private static final Logger logger = LogManager.getLogger(ContentProcessor.class);

	private static List<String> directDataInputLine;
	private static List<String> indirectDataInputLine;
	private static List<String> directQuestionInputLine;
	private static List<String> indirectQuestionInputLine;
	private static List<String> irrelevantQuestionInputLine;

	private static List<DirectData> directDataList;
	private static List<IndirectData> indirectDataList;
	private static List<Result> resultList;

	/**
	 * This method does the initial setup for the <b>processor</b>. This as the part
	 * of initial setup sets up the {@link DirectData} list, {@link IndirectData}
	 * list.
	 */
	private static void setUpData() {
		directDataInputLine = new ArrayList<String>();
		indirectDataInputLine = new ArrayList<String>();
		directQuestionInputLine = new ArrayList<String>();
		indirectQuestionInputLine = new ArrayList<String>();
		irrelevantQuestionInputLine = new ArrayList<String>();

		directDataList = new ArrayList<DirectData>();
		indirectDataList = new ArrayList<IndirectData>();
		resultList = new ArrayList<Result>();
	}

	/**
	 * This takes in the input lines of string questions and process them. It calls
	 * the {@link #setUpData()} for the initial variable initialization,
	 * {@link ContentProcessor#processDecider()} to decide and process the data,
	 * finally calls the {@link #buildResult()}
	 * 
	 * @param inputLines
	 * @return
	 */
	public static String processData(List<String> inputLines) {
		setUpData();
		classifyInput(inputLines);
		processDecider();
		String result = buildResult();
		return result;
	}

	/**
	 * This methods iterates through the input lines and invokes the
	 * {@link ContentClassifier} to classify the inputs into direct data, indirect
	 * data, direct question, indirect question, irrelevant question
	 * 
	 * @param inputLines
	 */
	public static void classifyInput(List<String> inputLines) {
		inputLines.forEach(inputLine -> {
			ContentClassifier.classifyInput(inputLine, directDataInputLine, indirectDataInputLine,
					directQuestionInputLine, indirectQuestionInputLine, irrelevantQuestionInputLine);
		});
	}

	/**
	 * Process Decider decides which Processor has to be invoked for the particular
	 * type of data input which is them processed by corresponding processors.
	 */
	private static void processDecider() {
		if (!directDataInputLine.isEmpty()) {
			DirectDataProcessor.processDirectData(directDataInputLine, directDataList);
		}
		if (!indirectDataInputLine.isEmpty()) {
			IndirectDataProcessor.processIndirectData(indirectDataInputLine, indirectDataList, directDataList);
		}
		if (!directQuestionInputLine.isEmpty()) {
			DirectQuestionProcessor.processDirectQuestion(directQuestionInputLine, directDataList, resultList);
		}
		if (!indirectQuestionInputLine.isEmpty()) {
			IndirectQuestionProcessor.processIndirectQuestion(indirectQuestionInputLine, directDataList,
					indirectDataList, resultList);
		}
		if (!irrelevantQuestionInputLine.isEmpty()) {
			IrrelevantQuestionProcessor.processIrreleventQuesion(irrelevantQuestionInputLine, resultList);
		}
	}

	/**
	 * <p>
	 * This methods calls the print method in {@link GtogUtil} which logs the data.
	 * This methods also helps in wrapping up the answer that is to be shown in the
	 * web page
	 * </p>
	 * 
	 * @return String answer
	 */
	private static String buildResult() {
		GtogUtil.print(directDataList, indirectDataList, resultList);
		StringBuilder answer = new StringBuilder(GtogConstants.EMPTY_STRING);
		resultList.forEach(result -> {
			answer.append(result.getAnswer());
			answer.append("/n");
		});
		return answer.toString();
	}

	/*
	 * these getters are to access the variable of the class outside the class
	 */

	public static List<String> getDirectDataInputLine() {
		return directDataInputLine;
	}

	public static List<String> getIndirectDataInputLine() {
		return indirectDataInputLine;
	}

	public static List<String> getDirectQuestionInputLine() {
		return directQuestionInputLine;
	}

	public static List<String> getIndirectQuestionInputLine() {
		return indirectQuestionInputLine;
	}

	public static List<String> getIrrelevantQuestionInputLine() {
		return irrelevantQuestionInputLine;
	}

	public static List<DirectData> getDirectDataList() {
		return directDataList;
	}

	public static List<IndirectData> getIndirectDataList() {
		return indirectDataList;
	}

	public static List<Result> getResultList() {
		return resultList;
	}

}
