package com.hhn.vs.centerapp.scraper;

import java.time.LocalDateTime;

import com.hhn.vs.centerapp.model.MeetingPlan;

/**
 * Meeting Manager Class
 * @author Felix Edel
 *
 */
public class MeetingManager {

	private static MeetingManager single_instance = null; 
  
    private MeetingPlan singleMeetingPlan = null;
    private MeetingService meetingService = null;
  
    /**
	 * Private constructor of the singleton
	 */
    private MeetingManager() 
    { 
    	meetingService = new MeetingService();
    	singleMeetingPlan = meetingService.scrapMeeting();
    } 
  
    /**
     * Gets the single instance of this class
     * @return MeetingManager instance
     */
    public static MeetingManager getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new MeetingManager(); 
  
        return single_instance; 
    }
    
    /**
     * Gets the meeting plan
     * @return The whole meeting plan object
     */
    public MeetingPlan getMeetingPlan() {
    	if(isMeetingPlanToday()) {
    		return singleMeetingPlan;
    	} else {
    		singleMeetingPlan = getNewMeetingPlan();
    		return singleMeetingPlan;
    	}
    }
    
    /**
     * Checks if the MeetingPlan is fresh
     * @return True when the meeting plan is fresh
     */
    private boolean isMeetingPlanToday() {
    	LocalDateTime nowLocalDate = LocalDateTime.now();
    	LocalDateTime currentLocalDate = LocalDateTime.parse(singleMeetingPlan.getCreated());
    	
    	int currentDayOfMonth = currentLocalDate.getDayOfMonth();
    	int nowCurrentDayOfMonth = nowLocalDate.getDayOfMonth();
    	
    	if((currentDayOfMonth - nowCurrentDayOfMonth) == 0) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * Gets a new object from the scraper
     * @return The fresh meeting plan
     */
    private MeetingPlan getNewMeetingPlan() {
    	return meetingService.scrapMeeting();
    }
}
