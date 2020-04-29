package com.hhn.vs.centerapp.scraper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hhn.vs.centerapp.model.Meeting;
import com.hhn.vs.centerapp.model.MeetingPlan;

/**
 * Meeting plan service that is based on a web crawler
 * @author Felix Edel
 *
 */
public class MeetingService {
	
	private static final Logger logger = LogManager.getLogger(MeetingService.class);
	
	/**
	 * Scraps the meeting plan from the url
	 * @return The meeting plan as a object
	 */
	public MeetingPlan scrapMeeting() {
		logger.debug("Get a new MeetingPlan object");
		
		String url = "https://www.hs-heilbronn.de/studentische-events";
		
		try {
			Document meetingDocument  = Jsoup.connect(url).get();
			Elements tableContent = meetingDocument.getElementsByClass("table-widget");
			
			MeetingPlan meetingPlan = new MeetingPlan();
			List<Meeting> meetingList = new ArrayList<>();
			
			
			for(Element element : tableContent) {
				
				Element tbody = element.select("tbody").get(0);
				Elements rows = tbody.getElementsByTag("tr");
				
				for(Element row : rows) {
					
					Elements cols = row.select("td");
					Meeting newMeeting = getMeetingFromElements(cols);
					if(newMeeting != null){
						meetingList.add(newMeeting);
					}
				}
			}
			
			meetingPlan.setMeetingList(meetingList);
			meetingPlan.setCreated(createTimestamp());
			
			logger.debug("New MeetingPlan: " + meetingPlan.toString());
			
			return meetingPlan;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Gets the meetings from the elements
	 * @param cols Column of the elements
	 * @return The meeting object
	 */
	private Meeting getMeetingFromElements(Elements cols) {
		try {
			String date = null;
			String title = null;
			String location = null;
			String time = null;
			
			int index = 0;
			for(Element value : cols) {
				System.out.println(value.toString());
				if(value.hasText()) {
					switch(index) {
					case 0:
						date = extractDate(value.text());
						DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yy");
						LocalDate dateLocalDate = LocalDate.parse(date, df);

						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						date = dateLocalDate.format(formatter);
						break;
					case 1:
						title = value.text();
						break;
					case 2:
						location = value.text();
						break;
					case 3:
						time = value.text();
						break;
					default:
						break;
					}
				}
				
				index++;
			}
			return new Meeting(date, title, location, time);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Extracts the date from the text
	 * @param text The given text
	 * @return The date
	 */
	private String extractDate(String text) {
		try {
			String[] stringArray = text.split(" ");
			
			if((stringArray[1] != null)) {
				return stringArray[1];
			}
			
			return text;
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error(e.getMessage(), e);
			return text;
		}
	}
	
	/**
	 * Creates a timestamp based on ISO8601 of the current date and time
	 * @return Timestamp string
	 */
	private String createTimestamp() {
		logger.debug("Create a ISO8601 timestamp");

		return LocalDateTime.now().toString();
	}
}
