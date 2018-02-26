package com.example.processor;

import java.util.List;

import com.example.constants.GtogConstants;

public class ContentClassifier {

	/**
	 * <p>
	 * This method classifies the input read from the input line into the following
	 * categories based on the conditions,
	 * <ul>
	 * <li>Direct input
	 * <li>Indirect input
	 * <li>Direct question
	 * <li>Indirect question
	 * <li>Irrelevent question
	 * </ul>
	 * </p>
	 * 
	 * @param inputLine
	 */
	public static void classifyInput(String inputLine, List<String> directDataInputLine, List<String> indirectDataInputLine,
			List<String> directQuestionInputLine, List<String> indirectQuestionInputLine,
			List<String> irrelevantQuestionInputLine) {
		if (inputLine.endsWith(GtogConstants.QUESTION_MARK)) {
			if (inputLine.toLowerCase().startsWith(GtogConstants.HOW_MUCH_IS)) {
				directQuestionInputLine.add(inputLine);
			} else if (inputLine.toLowerCase().startsWith(GtogConstants.HOW_MANY_CREDITS_IS)) {
				indirectQuestionInputLine.add(inputLine);
			} else {
				irrelevantQuestionInputLine.add(inputLine);
			}
		} else {
			if (inputLine.toLowerCase().endsWith(GtogConstants.CREDITS)) {
				indirectDataInputLine.add(inputLine);
			} else {
				directDataInputLine.add(inputLine);
			}
		}
	}

}
