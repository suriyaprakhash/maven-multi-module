package com.example.processor;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.Result;
import com.example.utils.GtogUtil;

/**
 * 
 * This is the direct question where in the computaion is directly based on the
 * <i>galactic data values</i> obtained by direct data and indirect data values
 * 
 * @version 1.0
 *
 */
public class DirectQuestionProcessor {

	private static final Logger logger = LogManager.getLogger(DirectQuestionProcessor.class);

	/**
	 * This method iterates through the <i>list of direct questions</i> and calls
	 * the {@link #processDirectQuestion()} method to process the question line
	 * 
	 * @param directQuestionInputLine
	 * @param directDataList
	 * @param resultList
	 */
	public static void processDirectQuestion(List<String> directQuestionInputLine, List<DirectData> directDataList,
			List<Result> resultList) {
		directQuestionInputLine.forEach(directQuestionLine -> {
			try {
				processDirectQuestion(directQuestionLine, directDataList, resultList);
			} catch (GtogException e) {
				logger.error("Invalid data for " + directQuestionInputLine);
				Result result = new Result();
				result.setQuetion(directQuestionLine);
				result.setAnswer(GtogConstants.I_HAVE_NO_IDEA);
				resultList.add(result);
			}
		});
	}

	/**
	 * 
	 * This is the helper method for direct question processing. It takes the
	 * <i>direct question line</i> and parse the line. Once data is <i>parsed</i>
	 * and <i>processed</i> with the help of {@link GtogUtil} it sets the
	 * corresponding <b>question</b> and <b>answer</b> into the {@link Result}
	 * 
	 * @param directQuestionLine
	 * @param directDataList
	 * @param resultList
	 * @throws GtogException
	 */
	private static void processDirectQuestion(String directQuestionLine, List<DirectData> directDataList,
			List<Result> resultList) throws GtogException {
		String[] individualQuestionData = directQuestionLine.split(GtogConstants.SPACE);
		Result result = new Result();
		StringBuilder galacticDataListQuestion = new StringBuilder(GtogConstants.EMPTY_STRING);
		double total = 0;
		int start = 3;// start after "how much is"
		int length = individualQuestionData.length - 1;// ends before "?"

		total = GtogUtil.galacticToDecimal(start, length, individualQuestionData, directDataList,
				galacticDataListQuestion);

		if (total == -1) {
			result.setQuetion(directQuestionLine);
			result.setAnswer("I have no idea what you are talking about");
			resultList.add(result);
		} else {
			result.setQuetion(directQuestionLine);
			result.setAnswer(galacticDataListQuestion.append("is ").append((int) total).toString());
		}

		resultList.add(result);

	}

}
