package com.example.model;

/**
 * This is the data object to store roman, galactic and number mappings
 * 
 * @version 1.0
 * @since 2018-02-14
 */
public class DirectData {
	private String roman;
	private String galactic;
	private double number;

	public DirectData() {

	}

	public DirectData(String roman, String galactic, double number) {
		this.roman = roman;
		this.galactic = galactic;
		this.number = number;
	}

	public String getRoman() {
		return roman;
	}

	public void setRoman(String roman) {
		this.roman = roman;
	}

	public String getGalactic() {
		return galactic;
	}

	public void setGalactic(String galactic) {
		this.galactic = galactic;
	}

	public double getNumber() {
		return number;
	}

	public void setNumber(double number) {
		this.number = number;
	}
}
