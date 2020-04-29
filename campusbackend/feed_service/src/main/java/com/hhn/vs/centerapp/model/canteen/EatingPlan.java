package com.hhn.vs.centerapp.model.canteen;

import java.util.List;

public class EatingPlan {
	String date;
	List<Dish> dishList;
	public EatingPlan(String date, List<Dish> dishList) {
		super();
		this.date = date;
		this.dishList = dishList;
	}
	
	public EatingPlan() {
		super();
	}

	public String getDate() {
		return date;
	}
	public List<Dish> getDishList() {
		return dishList;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDishList(List<Dish> dishList) {
		this.dishList = dishList;
	}
	@Override
	public String toString() {
		return "EatingPlan [date=" + date + ", dishList=" + dishList + "]";
	}
}
