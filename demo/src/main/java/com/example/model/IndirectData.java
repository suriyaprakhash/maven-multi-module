package com.example.model;

import java.util.List;

/**
 * This is a data object class to hold the data collected when an indirect data
 * line is processed
 * 
 * @version 1.0
 * @since 2018-02-14
 */
public class IndirectData {

	private List<String> galacticList;
	private double credit;
	private String metal;
	private double metalValue;

	public List<String> getGalacticList() {
		return galacticList;
	}

	public void setGalacticList(List<String> galacticList) {
		this.galacticList = galacticList;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public String getMetal() {
		return metal;
	}

	public void setMetal(String metal) {
		this.metal = metal;
	}

	public double getMetalValue() {
		return metalValue;
	}

	public void setMetalValue(double metalValue) {
		this.metalValue = metalValue;
	}

}
