package com.hhn.vs.centerapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hhn.vs.centerapp.model.Meeting;
import com.hhn.vs.centerapp.model.MeetingPlan;
import com.hhn.vs.centerapp.scraper.MeetingManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/meetingRest")
@Api(value="Meeting Management System")
public class MeetingController {
	
	private static final Logger logger = LogManager.getLogger(MeetingController.class);
	
	/**
	 * Gets a list of available meeting objects
	 * @return A list of meeting objects
	 */
	@ApiOperation(value = "Gets a list of available meetings")
	@RequestMapping(value = "/getAllData", method = RequestMethod.GET)
	@CrossOrigin
	public MeetingPlan getAllMeeting(){
		logger.info("/getAllData requested");
		
		return MeetingManager.getInstance().getMeetingPlan();
	}
	
	/**
	 * Gets a list of available meeting objects today
	 * @return A list of meeting objects today
	 */
	@ApiOperation(value = "Gets a list of available meetings today")
	@RequestMapping(value = "/getAllDataToday", method = RequestMethod.GET)
	@CrossOrigin
	public MeetingPlan getAllMeetingToday() {
		logger.info("/getAllDataToday requested");
		
		MeetingPlan newMeetingPlan = new MeetingPlan();
		
		MeetingPlan currentMeetingPlan = MeetingManager.getInstance().getMeetingPlan();
		newMeetingPlan.setCreated(currentMeetingPlan.getCreated());
		
		List<Meeting> meetingList = currentMeetingPlan.getMeetingList();
		List<Meeting> newMeetingList = new ArrayList<>();
		
		for(Meeting meeting : meetingList) {
			try {
				LocalDate localDate = LocalDate.now();//For reference
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				String formattedString = localDate.format(formatter);
				
				//Check if the date is today
				if(formattedString.equalsIgnoreCase(meeting.getDate())) {
					newMeetingList.add(meeting);
				}
				
			} catch (Exception e) {
			}
		}

		for(Meeting meeting : meetingList) {
			try {
				LocalDate localDate = LocalDate.now();//For reference
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
				String formattedString = localDate.format(formatter);

				//Check if the date is today
				if(formattedString.equalsIgnoreCase(meeting.getDate())) {
					newMeetingList.add(meeting);
				}

			} catch (Exception e) {
			}
		}
		
		newMeetingPlan.setMeetingList(newMeetingList);
		
		return newMeetingPlan;
	}
	
	/**
	 * Gets a list of available meeting objects last seven days
	 * @return A list of meeting objects last seven days
	 */
	@ApiOperation(value = "Gets a list of available meetings last seven days")
	@RequestMapping(value = "/getAllDataLastSevenDays", method = RequestMethod.GET)
	@CrossOrigin
	public MeetingPlan getAllMeetingLastSevenDays() {
		logger.info("/getAllMeetingLastSevenDays requested");
		
		MeetingPlan newMeetingPlan = new MeetingPlan();
		
		MeetingPlan currentMeetingPlan = MeetingManager.getInstance().getMeetingPlan();
		newMeetingPlan.setCreated(currentMeetingPlan.getCreated());
		
		List<Meeting> meetingList = currentMeetingPlan.getMeetingList();
		List<Meeting> newMeetingList = new ArrayList<>();
		List<String> dateList = getSevenDayListAsString();
		
		for(Meeting meeting : meetingList) {
			try {
				for(String date : dateList) {
					if(date.equalsIgnoreCase(meeting.getDate())) {
						newMeetingList.add(meeting);
					}
				}
				//Check if the date is today
				
			} catch (Exception e) {
			}
		}
		
		newMeetingPlan.setMeetingList(newMeetingList);
		
		return newMeetingPlan;
	}

	/**
	 * Gets a list of available meeting objects before today
	 * @return A list of meeting objects before today
	 */
	@ApiOperation(value = "Gets a list of available meetings before today")
	@RequestMapping(value = "/getAllDataBeforeToday", method = RequestMethod.GET)
	@CrossOrigin
	public MeetingPlan getAllMeetingBeforeToday() {
		logger.info("/getAllDataBeforeToday requested");

		MeetingPlan newMeetingPlan = new MeetingPlan();

		MeetingPlan currentMeetingPlan = MeetingManager.getInstance().getMeetingPlan();
		newMeetingPlan.setCreated(currentMeetingPlan.getCreated());

		List<Meeting> meetingList = currentMeetingPlan.getMeetingList();
		List<Meeting> newMeetingList = new ArrayList<>();

		LocalDate todayLocalDate = LocalDate.now();

		for(Meeting meeting : meetingList) {
			try {
				if(!meeting.getDate().isEmpty()){
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					LocalDate meetingLocalDate = LocalDate.parse(meeting.getDate(), formatter);

					if(meetingLocalDate.isBefore(todayLocalDate)){
						newMeetingList.add(meeting);
					}

					if(meetingLocalDate.isEqual(todayLocalDate)){
						newMeetingList.add(meeting);
					}
				}
			} catch (Exception e) {
			}
		}

		for(Meeting meeting : meetingList) {
			try {
				if(!meeting.getDate().isEmpty()){
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
					LocalDate meetingLocalDate = LocalDate.parse(meeting.getDate(), formatter);

					if(meetingLocalDate.isBefore(todayLocalDate)){


						newMeetingList.add(meeting);
					}

					if(meetingLocalDate.isEqual(todayLocalDate)){
						newMeetingList.add(meeting);
					}
				}
			} catch (Exception e) {
			}
		}

		newMeetingPlan.setMeetingList(newMeetingList);

		return newMeetingPlan;
	}
	
	/**
	 * Gets the status
	 */
	@ApiOperation(value = "Gets status of service", response = String.class)
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@CrossOrigin
	public void testAvailability() {
		
	}
	
	private List<String> getSevenDayListAsString(){
		List<String> dayList = new ArrayList<String>();
		
		LocalDate today = LocalDate.now();
		dayList.add(getDateAsStringYYYY(today));
		dayList.add(getDateAsStringYYYY(today.minusDays(1)));
		dayList.add(getDateAsStringYYYY(today.minusDays(2)));
		dayList.add(getDateAsStringYYYY(today.minusDays(3)));
		dayList.add(getDateAsStringYYYY(today.minusDays(4)));
		dayList.add(getDateAsStringYYYY(today.minusDays(5)));
		dayList.add(getDateAsStringYYYY(today.minusDays(6)));

		dayList.add(getDateAsStringYY(today));
		dayList.add(getDateAsStringYY(today.minusDays(1)));
		dayList.add(getDateAsStringYY(today.minusDays(2)));
		dayList.add(getDateAsStringYY(today.minusDays(3)));
		dayList.add(getDateAsStringYY(today.minusDays(4)));
		dayList.add(getDateAsStringYY(today.minusDays(5)));
		dayList.add(getDateAsStringYY(today.minusDays(6)));
		
		return dayList;
	}
	
	private String getDateAsStringYYYY(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		String formattedString = localDate.format(formatter);
		return formattedString;
	}

	private String getDateAsStringYY(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
		String formattedString = localDate.format(formatter);
		return formattedString;
	}
}
