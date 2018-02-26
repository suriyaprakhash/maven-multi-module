package com.example.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.utils.GtogUtil;

/**
 * The indirect data process the indrect data which has metal and galacitc
 * values in it. Ends with <i>credits</i> . Credit value is before the text
 * <i>credits</i> . Value is a <i>number</i>. Separator <i>space</i> should be
 * space
 * 
 * @version 1.0
 *
 */
public class IndirectDataProcessor {
	private static final Logger logger = LogManager.getLogger(IndirectDataProcessor.class);

	/**
	 * This method iterates through the <i>list of indirect data</i> and calls the
	 * {@link #processIndirectData()} method to process the data line
	 * 
	 * @param indirectDataInputLine
	 * @param indirectDataList
	 * @param directDataList
	 */
	public static void processIndirectData(List<String> indirectDataInputLine, List<IndirectData> indirectDataList,
			List<DirectData> directDataList) {
		indirectDataInputLine.forEach(indirectData -> {
			try {
				processIndirectData(indirectData, indirectDataList, directDataList);
			} catch (GtogException e) {
				logger.error("Invalid data for " + indirectData);
			}
		});
	}

	/**
	 * 
	 * This is the helper method for indirect data processing. It takes the
	 * <i>indirect data line</i> and parse the line. Once data is <i>parsed</i> and
	 * <i>processed</i> with the help of {@link GtogUtil} it adds the corresponding
	 * data into {@link IndirectData} list
	 * 
	 * @param indirectDataLine
	 * @param indirectDataList
	 * @param directDataList
	 * @throws GtogException
	 */
	private static void processIndirectData(String indirectDataLine, List<IndirectData> indirectDataList,
			List<DirectData> directDataList) throws GtogException {
		String[] individualData = indirectDataLine.split(GtogConstants.SPACE);
		int credit = 0;
		List<String> galacticList = new ArrayList<String>();
		String metal = GtogConstants.EMPTY_STRING;

		try {
			// getting the number before credit
			credit = Integer.parseInt(individualData[individualData.length - 2]);
		} catch (Exception e) {
			logger.error("invalid input data : indirect data : credit not a number ");
			throw new GtogException("invalid input data : indirect data : credit not a number ");
		}
		// getting galactic data
		for (int i = 0; i < individualData.length - 3; i++) {
			String currentElement = individualData[i];
			// add to galactic if directData's galactic string matches individualData string
			if (directDataList.stream().anyMatch(directData -> directData.getGalactic().equals(currentElement))) {
				galacticList.add(currentElement);
			}
			// else mark it as metal
			else {
				if (GtogConstants.EMPTY_STRING.equals(metal)) {
					metal = currentElement;
				} else {
					logger.error("invalid input data : indirect data : multiple metals detected");
					throw new GtogException("invalid input data : indirect data : multiple metals detected");
				}

			}
		}
		int start = 0;
		int length = galacticList.size();
		StringBuilder galacticDataList = new StringBuilder(GtogConstants.EMPTY_STRING);
		IndirectData indirectData = new IndirectData();
		double metalValue = GtogUtil.metalValueResolver(start, length, galacticList.stream().toArray(String[]::new),
				directDataList, galacticDataList, credit);
		if (metalValue == -1) {
			logger.error("invalid input data : indirect data : invalid metal value ");
			throw new GtogException("invalid input data : indirect data : invalid metal value ");
		}
		indirectData.setCredit(credit);
		indirectData.setGalacticList(galacticList);
		indirectData.setMetal(metal);
		indirectData.setMetalValue(metalValue);
		indirectDataList.add(indirectData);
	}

}
