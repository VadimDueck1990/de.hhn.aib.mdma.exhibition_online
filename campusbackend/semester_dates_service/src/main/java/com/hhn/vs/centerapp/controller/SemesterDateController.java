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

import com.hhn.vs.centerapp.model.SemesterDate;
import com.hhn.vs.centerapp.model.Timestamp;
import com.hhn.vs.centerapp.util.SemesterDateManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/semesterDateRest")
@Api(value="Semester date Management System")
public class SemesterDateController {
	
	private static final Logger logger = LogManager.getLogger(SemesterDateController.class);
	
	private Timestamp lastUpdateTimestamp = new Timestamp("");
	
	/**
	 * Gets a list of available semester date objects
	 * @return A list of semester date objects
	 */
	@ApiOperation(value = "Gets a list of available semester dates")
	@RequestMapping(value = "/getAllData", method = RequestMethod.GET)
	@CrossOrigin
	public List<SemesterDate> getAllSemesterDate(){
		logger.info("/getAllDataToday requested");
		
		List<SemesterDate> list = SemesterDateManager.getInstance().getSemesterDateList();

		return list;
	}
	
	/**
	 * Gets a list of available semester date objects of today
	 * @return A list of semester date objects of today
	 */
	@ApiOperation(value = "Gets a list of available semester dates today")
	@RequestMapping(value = "/getAllDataToday", method = RequestMethod.GET)
	@CrossOrigin
	public List<SemesterDate> getAllSemesterDateToday(){
		logger.info("/getAllDataToday requested");
		
		List<SemesterDate> semesterDateList = new ArrayList<>();
		List<SemesterDate> listToday = new ArrayList<>();
		
		//Get all semester date objects
		semesterDateList = SemesterDateManager.getInstance().getSemesterDateList();

		//Create a comparable string of today
		LocalDate localDate = LocalDate.now();//For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
		String formattedString = localDate.format(formatter);

		//Iterate through the semester date list
		for(SemesterDate semesterDate : semesterDateList) {
			
			try {
				//Check if the date is today
				if(formattedString.equalsIgnoreCase(semesterDate.getDateBegin())) {
					listToday.add(semesterDate);
				}
			} catch (Exception e) {
			}
		}
		
		return listToday;
	}
	
	/**
	 * Gets a list of available semester date objects of last seven days
	 * @return A list of semester date objects of last seven days
	 */
	@ApiOperation(value = "Gets a list of available semester dates last seven days")
	@RequestMapping(value = "/getAllDataLastSevenDays", method = RequestMethod.GET)
	@CrossOrigin
	public List<SemesterDate> getAllSemesterDateLastSevenDays(){
		logger.info("/getAllDataLastSevenDays requested");
		
		List<SemesterDate> semesterDateList = new ArrayList<>();
		List<SemesterDate> listToday = new ArrayList<>();
		
		//Get all semester date objects
		semesterDateList = SemesterDateManager.getInstance().getSemesterDateList();

		//Create a comparable list of string of the last seven days
		List<String> dateList = getSevenDayListAsString();

		//Iterate through the semester date list
		for(SemesterDate semesterDate : semesterDateList) {
			try {
				for(String date : dateList) {
					//Check if the date is today
					if(date.equalsIgnoreCase(semesterDate.getDateBegin())) {
						listToday.add(semesterDate);
					}
				}
			} catch (Exception e) {
			}
		}
		
		return listToday;
	}

	/**
	 * Gets a list of available semester date objects of before today
	 * @return A list of semester date objects of before today
	 */
	@ApiOperation(value = "Gets a list of available semester dates before today")
	@RequestMapping(value = "/getAllDataBeforeToday", method = RequestMethod.GET)
	@CrossOrigin
	public List<SemesterDate> getAllSemesterDateBeforeToday(){
		logger.info("/getAllDataBeforeToday requested");

		//Get all semester date objects
		List<SemesterDate> semesterDateList = SemesterDateManager.getInstance().getSemesterDateList();
		List<SemesterDate> listBeforeToday = new ArrayList<>();

		LocalDate todayLocalDate = LocalDate.now();

		//Iterate through the semester date list
		for(SemesterDate semesterDate : semesterDateList) {
			try {
				if(!semesterDate.getDateBegin().isEmpty()){
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
					LocalDate semesterDateLocalDate = LocalDate.parse(semesterDate.getDateBegin(), formatter);

					if(semesterDateLocalDate.isBefore(todayLocalDate)){
						listBeforeToday.add(semesterDate);
					}

					if(semesterDateLocalDate.isEqual(todayLocalDate)){
						listBeforeToday.add(semesterDate);
					}
				}
			} catch (Exception e) {
			}
		}

		return listBeforeToday;
	}

	
	/**
	 * Gets a last update timestamp
	 * @return Timestamp object
	 */
	@ApiOperation(value = "Gets a last update timestamp", response = Timestamp.class)
	@RequestMapping(value = "/lastServiceUpdate", method = RequestMethod.GET)
	@CrossOrigin
	public Timestamp lastUpdate() {
		logger.info("/lastServiceUpdate requested");
		return lastUpdateTimestamp;
	}
	
	/**
	 * Gets the status of the service
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
		dayList.add(getDateAsString(today));
		dayList.add(getDateAsString(today.minusDays(1)));
		dayList.add(getDateAsString(today.minusDays(2)));
		dayList.add(getDateAsString(today.minusDays(3)));
		dayList.add(getDateAsString(today.minusDays(4)));
		dayList.add(getDateAsString(today.minusDays(5)));
		dayList.add(getDateAsString(today.minusDays(6)));
		
		return dayList;
	}
	
	private String getDateAsString(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
		String formattedString = localDate.format(formatter);
		return formattedString;
	}
}
