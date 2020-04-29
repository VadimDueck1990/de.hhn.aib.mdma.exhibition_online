package com.hhn.vs.centerapp.model;

public class Meeting {
	String date;
	String title;
	String location;
	String time;
	public Meeting(String date, String title, String location, String time) {
		super();
		this.date = date;
		this.title = title;
		this.location = location;
		this.time = time;
	}
	public Meeting() {
		super();
	}
	public String getDate() {
		return date;
	}
	public String getTitle() {
		return title;
	}
	public String getLocation() {
		return location;
	}
	public String getTime() {
		return time;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Meeting [date=" + date + ", title=" + title + ", location=" + location + ", time=" + time + "]";
	}
	
}
