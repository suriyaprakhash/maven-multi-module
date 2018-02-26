package com.example.processor;

import java.util.List;

import com.example.constants.GtogConstants;
import com.example.model.Result;

/**
 * This is the class process the irrelevent question that is not part of the
 * galactic context
 * 
 * @version 1.0
 *
 */
public class IrrelevantQuestionProcessor {

	/**
	 * This method iterates and sets the <i>irrelevant question</i> form the list
	 * and also sets the <i>answer</i> into the {@link Result} object
	 * 
	 * @param irrelevantQuestionInputLine
	 * @param resultList
	 */
	public static void processIrreleventQuesion(List<String> irrelevantQuestionInputLine, List<Result> resultList) {
		irrelevantQuestionInputLine.forEach(irreleventQuestionLine -> {
			Result result = new Result();
			result.setQuetion(irreleventQuestionLine);
			result.setAnswer(GtogConstants.I_HAVE_NO_IDEA);
			resultList.add(result);
		});
	}

}
