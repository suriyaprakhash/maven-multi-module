package com.example.processor;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.constants.GtogConstants;
import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.utils.GtogUtil;

/**
 * The input wherein data is extracted directly is direct input <i>eg.</i>pish
 * is X. It involves mapping of roman numerals against the galactic value. This
 * process the direct input and adds it to the {@link DirectData} list. These
 * input has to be 3 split with <i>space</i> seperator. <i> is </i> is expected
 * in the middle of the input. <i>Galactic string</i> is expeted before
 * <i>is</i> and can be composed of <i>numbers, string</i> . <i>Roman
 * numeral</i> after <i> is </i> is expected
 * 
 * @version 1.0
 *
 */
public class DirectDataProcessor {

	private static final Logger logger = LogManager.getLogger(DirectDataProcessor.class);

	/**
	 * This method iterates through the <i>list of direct data</i> and calls the
	 * {@link #processDirectData()} method to process the data line
	 * 
	 * @param directDataInputLineList
	 * @param directDataList
	 */
	public static void processDirectData(List<String> directDataInputLineList, List<DirectData> directDataList) {
		directDataInputLineList.forEach(directData -> {
			try {
				processDirectData(directData, directDataList);
			} catch (GtogException e) {
				logger.error("Invalid data for " + directData);
			}
		});
	}

	/**
	 * This is the helper method for direct data processing. It takes the <i>direct
	 * data line</i> and parse the line. Once data is <i>parsed</i> and
	 * <i>processed</i> with the help of {@link GtogUtil} it adds the corresponding
	 * data into {@link DirectData} list
	 * 
	 * @param directData
	 * @param directDataList
	 * @throws GtogException
	 */
	private static void processDirectData(String directData, List<DirectData> directDataList) throws GtogException {
		String[] individualData = directData.split(GtogConstants.SPACE);
		if (individualData.length == 3) {
			String galactic = GtogConstants.EMPTY_STRING, roman = GtogConstants.EMPTY_STRING;
			galactic = individualData[0];
			if (individualData[2] instanceof String && individualData[2].length() == 1) {
				roman = individualData[2];
			} else {
				logger.error("invalid input data : direct data : 2 : not roman");
				throw new GtogException("invalid input data : direct data : 2 : not roman");
			}
			if (GtogConstants.IS.toLowerCase().equals(individualData[1])) {
				DirectData data = new DirectData();
				data.setGalactic(galactic);
				data.setRoman(roman);
				data.setNumber(GtogUtil.getNumberForRoman(roman));
				directDataList.add(data);
			} else {
				logger.error("invalid input data : direct data : 1 : not `is`");
				throw new GtogException("invalid input data : direct data : 1 : not `is`");
			}

		} else {
			logger.error("invalid input data : direct data : length mismatch");
			throw new GtogException("invalid input data : direct data : length mismatch");
		}
	}

}
