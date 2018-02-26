package com.example.processor;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.model.Result;
import com.example.utils.GtogUtil;

/**
 * This process the indirect Questions inputs where the computation involves the
 * usage of <i>galactic data</i> and the <i>metal data</i> obtained from the
 * direct and indirect data inputs.Should start with <i>how many Credits is </i>. Ends with <i>
 * ?</i> Has one or many <i>galactic</i> string and <i>one metal</i> string.
 * Spearated by <i>space</i>
 * 
 * @version 1.0
 *
 */
public class IndirectQuestionProcessor {
	private static final Logger logger = LogManager.getLogger(IndirectQuestionProcessor.class);

	/**
	 * This method iterates through the <i>list of indirect questions</i> and calls
	 * the {@link #processIndirectQuestion()} method to process the question line
	 * 
	 * @param indirectQuestionInputLine
	 * @param directDataList
	 * @param indirectDataList
	 * @param resultList
	 */
	public static void processIndirectQuestion(List<String> indirectQuestionInputLine, List<DirectData> directDataList,
			List<IndirectData> indirectDataList, List<Result> resultList) {
		indirectQuestionInputLine.forEach(indirectQuestionLine -> {
			try {
				processIndirectQuestion(indirectQuestionLine, directDataList, indirectDataList, resultList);
			} catch (GtogException e) {
				logger.error("Invalid data for " + indirectQuestionInputLine);
				Result result = new Result();
				result.setQuetion(indirectQuestionLine);
				result.setAnswer(GtogConstants.I_HAVE_NO_IDEA);
				resultList.add(result);
			}
		});
	}

	/**
	 * This is the helper method for indirect question processing. It takes the
	 * <i>indirect question line</i> and parse the line. Once data is <i>parsed</i>
	 * and <i>processed</i> with the help of {@link GtogUtil} it sets the
	 * corresponding <b>question</b> and <b>answer</b> into the {@link Result}
	 * 
	 * @param indirectQuestionLine
	 * @param directDataList
	 * @param indirectDataList
	 * @param resultList
	 * @throws GtogException
	 */
	private static void processIndirectQuestion(String indirectQuestionLine, List<DirectData> directDataList,
			List<IndirectData> indirectDataList, List<Result> resultList) throws GtogException {
		String[] individualQuestionData = indirectQuestionLine.split(GtogConstants.SPACE);
		Result result = new Result();
		StringBuilder galacticDataListQuestion = new StringBuilder(GtogConstants.EMPTY_STRING);
		double total = 0;
		int start = 4;// start after "how many credits is"
		int length = individualQuestionData.length - 2;// ends before "?"

		total = GtogUtil.galacticToDecimal(start, length, individualQuestionData, directDataList,
				galacticDataListQuestion);

		if (total == -1) {
			result.setQuetion(indirectQuestionLine);
			result.setAnswer(GtogConstants.I_HAVE_NO_IDEA);
			resultList.add(result);
		} else {
			double metalValue = GtogUtil.fetchMetalValue(individualQuestionData[individualQuestionData.length - 2],
					indirectDataList);
			result.setQuetion(indirectQuestionLine);
			result.setAnswer(galacticDataListQuestion.append(individualQuestionData[individualQuestionData.length - 2])
					.append(" is ").append((int) (metalValue * total)).toString());
		}

		resultList.add(result);

	}

}
