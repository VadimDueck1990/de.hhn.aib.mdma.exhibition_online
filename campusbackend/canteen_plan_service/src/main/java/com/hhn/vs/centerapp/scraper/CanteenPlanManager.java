package com.hhn.vs.centerapp.scraper;

import java.time.LocalDateTime;

import com.hhn.vs.centerapp.model.Canteen;

/**
 * Canteen Manager Class
 * @author Felix Edel
 *
 */
public class CanteenPlanManager {
	
	private static CanteenPlanManager single_instance = null;
	
	private Canteen singleCanteen = null;
	private CanteenPlanScraper scraper = null;
	
	/**
	 * Private constructor of the singleton
	 */
	private CanteenPlanManager() {
		scraper = new CanteenPlanScraper();		
		singleCanteen = scraper.scrapCanteenPlan();
	}
	
	/**
	 * Gets the single instance of this class
	 * @return CanteenPlanManager instance
	 */
	public static CanteenPlanManager getInstance() {
		if(single_instance == null) {
			single_instance = new CanteenPlanManager();
		}
		return single_instance;
	}
	
	/**
	 * Gets the canteen object
	 * @return The whole canteen object
	 */
	public Canteen getCanteen() {
		if(isCanteenToday()) {
			return singleCanteen;
		} else {
			singleCanteen = getNewCanteen();
			return singleCanteen;
		}
	}
	
	/**
	 * Checks if the canteen object is fresh
	 * @return True when the meeting plan is fresh
	 */
	private boolean isCanteenToday() {
		LocalDateTime nowLocalDate = LocalDateTime.now();
    	LocalDateTime currentLocalDate = LocalDateTime.parse(singleCanteen.getCreated());
    	
    	int currentDayOfMonth = currentLocalDate.getDayOfMonth();
    	int nowCurrentDayOfMonth = nowLocalDate.getDayOfMonth();
    	
    	if((currentDayOfMonth - nowCurrentDayOfMonth) == 0) {
    		return true;
    	}
    	
    	return false;
	}
	
	/**
     * Gets a new object from the scraper
     * @return The fresh canteen object
     */
	private Canteen getNewCanteen() {
		return scraper.scrapCanteenPlan();
	}
}
