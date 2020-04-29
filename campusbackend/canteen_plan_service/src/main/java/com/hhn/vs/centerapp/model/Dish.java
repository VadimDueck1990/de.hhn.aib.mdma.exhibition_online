package com.hhn.vs.centerapp.model;

public class Dish {
	String name;
	String output;
	String studEur;
	String bedEur;
	String gastEur;
	public Dish(String name, String output, String studEur, String bedEur, String gastEur) {
		super();
		this.name = name;
		this.output = output;
		this.studEur = studEur;
		this.bedEur = bedEur;
		this.gastEur = gastEur;
	}
	public Dish() {
		super();
	}
	public String getName() {
		return name;
	}
	public String getOutput() {
		return output;
	}
	public String getStudEur() {
		return studEur;
	}
	public String getBedEur() {
		return bedEur;
	}
	public String getGastEur() {
		return gastEur;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public void setStudEur(String studEur) {
		this.studEur = studEur;
	}
	public void setBedEur(String bedEur) {
		this.bedEur = bedEur;
	}
	public void setGastEur(String gastEur) {
		this.gastEur = gastEur;
	}
	@Override
	public String toString() {
		return "Dish [name=" + name + ", output=" + output + ", studEur=" + studEur + ", bedEur=" + bedEur
				+ ", gastEur=" + gastEur + "]";
	}
	
}
