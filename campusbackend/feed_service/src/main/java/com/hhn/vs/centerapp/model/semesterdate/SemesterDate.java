package com.hhn.vs.centerapp.model.semesterdate;

public class SemesterDate {
	private String id;
	private String dateBegin; //must ISO8601 Day + time
	private String dateEnd; //null
	private String title; //must
	private String description; //must
	private String category; //must
	private String created;
	private String lastUpdate;
	public SemesterDate(String id, String dateBegin, String dateEnd, String title, String description, String category,
			String created, String lastUpdate) {
		super();
		this.id = id;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.title = title;
		this.description = description;
		this.category = category;
		this.created = created;
		this.lastUpdate = lastUpdate;
	}
	public SemesterDate() {
		super();
	}
	public String getId() {
		return id;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getCategory() {
		return category;
	}
	public String getCreated() {
		return created;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@Override
	public String toString() {
		return "SemesterDate [id=" + id + ", dateBegin=" + dateBegin + ", dateEnd=" + dateEnd + ", title=" + title
				+ ", description=" + description + ", category=" + category + ", created=" + created + ", lastUpdate="
				+ lastUpdate + "]";
	}
	
}