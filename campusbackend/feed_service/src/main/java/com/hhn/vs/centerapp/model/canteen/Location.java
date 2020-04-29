package com.hhn.vs.centerapp.model.canteen;

import java.util.List;

public class Location {
	String name;
	List<EatingPlan> eatingPlanList;
	public Location(String name, List<EatingPlan> eatingPlanList) {
		super();
		this.name = name;
		this.eatingPlanList = eatingPlanList;
	}
	
	public Location(String name) {
		super();
		this.name = name;
	}

	public Location() {
		super();
	}
	public String getName() {
		return name;
	}
	public List<EatingPlan> getEatingPlanList() {
		return eatingPlanList;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEatingPlanList(List<EatingPlan> eatingPlanList) {
		this.eatingPlanList = eatingPlanList;
	}
	@Override
	public String toString() {
		return "Location [name=" + name + ", eatingPlanList=" + eatingPlanList + "]";
	}
}
