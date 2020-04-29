package com.hhn.vs.centerapp.util;

public final class Constants {
	public final class SwaggerConfig {
		public static final String TITLE = "HHNCenterApp Feed Service REST API";
		public static final String DESCRIPTION = "A project of the University of Heilbronn for the course Distributed Systems. Members of the group HHN Center App are Felix Edel, Friedrich Greiner and Shiqqi Wang.";
		public static final String CONTACT_NAME = "Felix Edel";
		public static final String CONTACT_EMAIL = "fedel@stud.hs-heilbronn.de";
		public static final String CONTACT_WEBSITE = "www.scurrburr.net"; 
		public static final String VERSION = "1.0.0"; 
	}

	public final class Addresses {
		public static final String URL_ADDRESS_CANTEEN_ALL = "http://canteenrestapi:8085/api/canteenPlanRest/getAllData";
		public static final String URL_ADDRESS_CANTEEN_TODAY = "http://canteenrestapi:8085/api/canteenPlanRest/getAllDataToday";
		public static final String URL_ADDRESS_CANTEEN_TODAY_CAMPUS = "http://canteenrestapi:8085/api/canteenPlanRest/getAllDataFromBildungscampusToday";
		public static final String URL_ADDRESS_CANTEEN_TODAY_SONTHEIM = "http://canteenrestapi:8085/api/canteenPlanRest/getAllDataFromSontheimToday";
		public static final String URL_ADDRESS_CANTEEN_TODAY_KUENZELSAU = "http://canteenrestapi:8085/api/canteenPlanRest/getAllDataFromKuenzelsauToday";
		public static final String URL_ADDRESS_CANTEEN_STATUS = "http://canteenrestapi:8085/api/canteenPlanRest/status";

		public static final String URL_ADDRESS_MEETING_ALL = "http://meetingrestapi:8083/api/meetingRest/getAllData";
		public static final String URL_ADDRESS_MEETING_TODAY = "http://meetingrestapi:8083/api/meetingRest/getAllDataToday";
		public static final String URL_ADDRESS_MEETING_SEVEN_DAYS = "http://meetingrestapi:8083/api/meetingRest/getAllDataLastSevenDays";
		public static final String URL_ADDRESS_MEETING_BEFORE_TODAY = "http://meetingrestapi:8083/api/meetingRest/getAllDataBeforeToday";
		public static final String URL_ADDRESS_MEETING_STATUS = "http://meetingrestapi:8083/api/meetingRest/status";

		public static final String URL_ADDRESS_SEMESTER_DATES_ALL = "http://semesterdatesrestapi:8084/api/semesterDateRest/getAllData";
		public static final String URL_ADDRESS_SEMESTER_DATES_TODAY = "http://semesterdatesrestapi:8084/api/semesterDateRest/getAllDataToday";
		public static final String URL_ADDRESS_SEMESTER_DATES_SEVEN_DAYS = "http://semesterdatesrestapi:8084/api/semesterDateRest/getAllDataLastSevenDays";
		public static final String URL_ADDRESS_SEMESTER_DATES_BEFORE_TODAY = "http://semesterdatesrestapi:8084/api/semesterDateRest/getAllDataBeforeToday";
		public static final String URL_ADDRESS_SEMESTER_DATES_STATUS = "http://semesterdatesrestapi:8084/api/semesterDateRest/status";

		public static final String URL_ADDRESS_GROUP_MEETING_ALL = "";
		public static final String URL_ADDRESS_GROUP_MEETING_TODAY = "";
		public static final String URL_ADDRESS_GROUP_MEETING_SEVEN_DAYS = "";
		public static final String URL_ADDRESS_GROUP_MEETING_STATUS = "";
	}
}
