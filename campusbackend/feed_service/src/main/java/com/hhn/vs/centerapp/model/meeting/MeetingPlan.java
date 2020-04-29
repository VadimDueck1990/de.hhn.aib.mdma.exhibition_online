package com.hhn.vs.centerapp.model.meeting;

import java.util.List;

public class MeetingPlan {
	String created;
	List<Meeting> meetingList;
	public MeetingPlan(String created, List<Meeting> meetingList) {
		super();
		this.created = created;
		this.meetingList = meetingList;
	}
	public MeetingPlan() {
		super();
	}
	public String getCreated() {
		return created;
	}
	public List<Meeting> getMeetingList() {
		return meetingList;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public void setMeetingList(List<Meeting> meetingList) {
		this.meetingList = meetingList;
	}
	@Override
	public String toString() {
		return "MeetingPlan [created=" + created + ", meetingList=" + meetingList + "]";
	}
}
