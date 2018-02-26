package com.example.utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.example.exception.GtogException;
import com.example.model.DirectData;
import com.example.model.IndirectData;
import com.example.model.Result;

/**
 * This is the utility class for Gtog. Where it has methods for identifying the
 * roman numbers, calculate metal vale and galactic to decimal checks
 * 
 * @version 1.0
 */
public class GtogUtil {
	private static final Logger logger = LogManager.getLogger(GtogUtil.class);

	/**
	 * This method determines the metal value in decimal
	 * 
	 * @param start
	 * @param length
	 * @param galacticSymbolArray
	 * @param directDataList
	 * @param galacticDataQuestionBuilder
	 * @param credits
	 * @return
	 */
	public static double metalValueResolver(int start, int length, String[] galacticSymbolArray,
			List<DirectData> directDataList, StringBuilder galacticDataQuestionBuilder, double credits) {
		double metalValue = 0;
		double total = 0;
		try {
			total = galacticToDecimal(start, length, galacticSymbolArray, directDataList, galacticDataQuestionBuilder);
		} catch (GtogException e) {
			logger.error("indirect data : metal value not determined : no matching galactic symbol found");
			return -1;
		}
		if (total != -1) {
			metalValue = credits / total;
		} else {
			metalValue = -1;
		}
		return metalValue;
	}

	/**
	 * This method is used for galactic to decimal conversion. It includes has
	 * repeat check and subtract checks
	 * 
	 * @param start
	 * @param length
	 * @param galacticSymbolArray
	 * @param directDataList
	 * @param galacticDataQuestionBuilder
	 * @return
	 * @throws GtogException
	 */
	public static double galacticToDecimal(int start, int length, String[] galacticSymbolArray,
			List<DirectData> directDataList, StringBuilder galacticDataQuestionBuilder) throws GtogException {
		double next = 0;
		int count = 1;
		double current = 0;
		double total = 0;
		double repeatElement = 0; // to figure out repeat element
		boolean checkEmpty=true; //to check if  galactical presetnt 
		for (int i = start; i < length; i++) {
			// get the decimal for galactic symbol
			current = getDecimalValue(galacticSymbolArray[i], directDataList);

			if (i + 1 < length) {
				checkEmpty=false;
				// get the next decimal value
				next = getDecimalValue(galacticSymbolArray[i + 1], directDataList);

				if (current > next) {
					total = total + current;
					galacticDataQuestionBuilder.append(galacticSymbolArray[i] + " ");
					count = 1; // reset count to 1
				} else if (current == next) {
					count++;
					checkRepetition(galacticSymbolArray, count, current, i);
					total = total + current;
					galacticDataQuestionBuilder.append(galacticSymbolArray[i] + " ");
					repeatElement = current;
				} else {
					checkIfSubtractable(galacticSymbolArray, next, current, i);
					total = total + next - current;
					galacticDataQuestionBuilder.append(galacticSymbolArray[i] + " ");
					i++;
					// adding the next element
					galacticDataQuestionBuilder.append(galacticSymbolArray[i] + " ");
					// reset count to 1
					count = 1;
				}
			} else {
				checkEmpty=false;
				// this condition to check if the last element is getting repeated
				if (repeatElement == current) {
					checkRepetition(galacticSymbolArray, count, current, i);
				}
				total = total + current;
				galacticDataQuestionBuilder.append(galacticSymbolArray[i] + " ");
				i++;
			}

		}
		if(checkEmpty) {
			logger.error("No galactic sysmbol found");
			throw new GtogException("No galactic symbol found");
		}
		return total;
	}

	/**
	 * This utility method checks if 1,10,100,1000 is not detectable from higher
	 * order digits. Also check 5,50,500 is not getting subtracted
	 * 
	 * @param galacticSymbolArray
	 * @param next
	 * @param current
	 * @param i
	 * @throws GtogException
	 */
	private static void checkIfSubtractable(String[] galacticSymbolArray, double next, double current, int i)
			throws GtogException {
		if (!(((current == 1) && (next == 5 || next == 10)) || ((current == 10) && (next == 50 || next == 100))
				|| ((current == 100) && (next == 500 || next == 1000)))) {
			logger.error(galacticSymbolArray[i] + ":" + current + " cannot be subtracted from " + galacticSymbolArray[i]
					+ ":" + next);
			throw new GtogException(galacticSymbolArray[i] + ":" + current + " cannot be subtracted from "
					+ galacticSymbolArray[i] + ":" + next);
		}
		if (current == 5 || current == 50 || current == 500) {
			logger.error(galacticSymbolArray[i] + ":" + current + " can never be subtracted");
			throw new GtogException(galacticSymbolArray[i] + ":" + current + " can never be subtracted");
		}
	}

	/**
	 * This check for 1,10,100,1000 repeats not more than 3. And also check the
	 * 5,50,500 is not repeated
	 * 
	 * @param galacticSymbolArray
	 * @param count
	 * @param current
	 * @param i
	 * @throws GtogException
	 */
	private static void checkRepetition(String[] galacticSymbolArray, int count, double current, int i)
			throws GtogException {
		if ((current == 1 || current == 10 || current == 100 || current == 1000) && count == 4) {
			logger.error(galacticSymbolArray[i] + "of decimal value " + current + " repeated more than 3 times");
			throw new GtogException(
					galacticSymbolArray[i] + "of decimal value " + current + " repeated more than 3 times");
		}
		if ((current == 5 || current == 50 || current == 500) && count == 2) {
			logger.error(galacticSymbolArray[i] + "of decimal value " + current + " repeated");
			throw new GtogException(galacticSymbolArray[i] + "of decimal value " + current + " repeated");
		}
	}

	/**
	 * This returns the decimal value from the Direct data mapping
	 * 
	 * @param galacticSymbol
	 * @param directDataList
	 * @return
	 * @throws GtogException
	 */
	private static double getDecimalValue(String galacticSymbol, List<DirectData> directDataList) throws GtogException {
		double decimalValue = 0;
		try {
			Optional<DirectData> directData = directDataList.stream()
					.filter(ddl -> ddl.getGalactic().equals(galacticSymbol)).findFirst();
			decimalValue = directData.get().getNumber();
		} catch (NoSuchElementException e) {
			logger.error("direct or indirect question : error from calc : no matching galactic symbols found");
			throw new GtogException("No galactic matched with the available data", e);
		}
		return decimalValue;
	}

	/**
	 * This fetches the metal value form the direct data mapping
	 * 
	 * @param metal
	 * @param indirectDataList
	 * @return
	 * @throws GtogException
	 */
	public static double fetchMetalValue(String metal, List<IndirectData> indirectDataList) throws GtogException {
		double metalValue = 0;
		try {
			Optional<IndirectData> idd = indirectDataList.stream().filter(data -> data.getMetal().equals(metal))
					.findFirst();
			metalValue = idd.get().getMetalValue();
		} catch (NoSuchElementException e) {
			logger.error("inderct question : No such metal found");
			throw new GtogException("No such metal found");
		}
		return metalValue;
	}

	/**
	 * This gets the corresponding decimal value for the roman char
	 * 
	 * @param roman
	 * @return
	 * @throws GtogException
	 */
	public static double getNumberForRoman(String roman) throws GtogException {
		int number = 0;
		boolean isRoman = false;
		switch (roman.toLowerCase()) {
		case "i":
			number = 1;
			isRoman = true;
			break;
		case "v":
			number = 5;
			isRoman = true;
			break;
		case "x":
			number = 10;
			isRoman = true;
			break;
		case "l":
			number = 50;
			isRoman = true;
			break;
		case "c":
			number = 100;
			isRoman = true;
			break;
		case "d":
			number = 500;
			isRoman = true;
			break;
		case "m":
			number = 1000;
			isRoman = true;
			break;
		default:
			break;
		}

		if (isRoman == false) {
			logger.error(number + " not a roman number");
			throw new GtogException(number + " not a roman number");
		}
		return number;

	}

	/**
	 * This is the utility method to print the entire result
	 * 
	 * @param directDataList
	 * @param indirectDataList
	 * @param resultList
	 */
	public static void print(List<DirectData> directDataList, List<IndirectData> indirectDataList,
			List<Result> resultList) {
		logger.info("------------Direct data------------");
		directDataList.forEach(data -> {
			logger.info(data.getGalactic() + "-" + data.getNumber() + "-" + data.getRoman());
		});
		logger.info("------------Indirect data------------");
		indirectDataList.forEach(data -> {
			data.getGalacticList().forEach(galacticData -> logger.info(galacticData + " "));
			logger.info("-" + data.getCredit() + "-" + data.getMetal() + "-" + data.getMetalValue());
		});
		logger.info("------------Result------------");
		resultList.forEach(data -> {
			logger.info(data.getAnswer());
		});

	}

}
