package com.hhn.vs.centerapp.model.canteen;

import java.util.List;

public class Canteen {
	List<Location> locationList;
	String created;

	public Canteen(List<Location> locationList, String created) {
		super();
		this.locationList = locationList;
	}

	public Canteen() {
		super();
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
	
	
	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Canteen [locationList=" + locationList + ", created=" + created + "]";
	}
}
