package com.hhn.vs.centerapp.scraper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hhn.vs.centerapp.model.Canteen;
import com.hhn.vs.centerapp.model.Dish;
import com.hhn.vs.centerapp.model.EatingPlan;
import com.hhn.vs.centerapp.model.Location;

public class CanteenPlanScraper {
	
	private static final Logger logger = LogManager.getLogger(CanteenPlanScraper.class);
	
	/**
	 * Scraps all important information of the eating plan from the url
	 * @return Cateen object with all information
	 */
	public Canteen scrapCanteenPlan() {
		logger.debug("Get a new CanteenPlan object");
		
		String hnUrl = "https://www.studentenwerk.uni-heidelberg.de/node/136";
		String kUrl = "https://www.studentenwerk.uni-heidelberg.de/de/node/137";

		try {
			Document canteenPlanDocumentHN = Jsoup.connect(hnUrl).get();
			Document canteenPlanDocumentK = Jsoup.connect(kUrl).get();
			
			System.out.println(canteenPlanDocumentHN.toString());
			
			Canteen canteen = new Canteen();
			
			List<Location> locationList = new ArrayList<Location>();
			
			Location sontheim = new Location("Sontheim");
			Location bildungscampus = new Location("Bildungscampus");
			Location kuenzelsau = new Location("Kuenzelsau");
			
			//Gets both location of canteen (Sontheim and Bildungscampus)
			Elements canteenLocation = canteenPlanDocumentHN.getElementsByClass("mensa-carousel-wrapper-2");
			Elements canteenLocationKOrigin = canteenPlanDocumentK.getElementsByClass("mensa-carousel-wrapper-2");
			
			canteenLocation.addAll(canteenLocationKOrigin);
			System.out.println(canteenLocation);
			
			int index = 0;
			//Gets one of the specific canteen location (First Sontheim then Bildungscampus)
			for(Element specificCanteenLocation : canteenLocation) {
				
				Elements list = specificCanteenLocation.getElementsByClass("item");
				List<EatingPlan> eatingPlanList = new ArrayList<EatingPlan>();
				
				//Gets one of dish plan out
				for(Element oneDatePlan : list) {
					EatingPlan eatingPlan = new EatingPlan();
					
					//Extract date
					String date = extractDate(oneDatePlan.getElementsByTag("h4").text());
					eatingPlan.setDate(date);
					
					//Check if there is content
					if(!oneDatePlan.select("table").isEmpty()) {
						Element table = oneDatePlan.select("table").get(0); //select the first table.
						Elements rows = table.select("tr");

						Dish dishDetail = null;
						List<Dish> dishList = new ArrayList<>();
						
						//Gets all content from that one day
						for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
							
						    Element row = rows.get(i);
						    Elements cols = row.select("td");
						    
						    dishDetail = extractDishFromElements(cols);
						    dishList.add(dishDetail);
						}
						eatingPlan.setDishList(dishList);
					}
					eatingPlanList.add(eatingPlan);
				}
				
				//Add to sontheim if index is zero
				if(index==0) {
					sontheim.setEatingPlanList(eatingPlanList);
					locationList.add(sontheim);
				} if(index==1) { //Add to bildungscampus if index is one
					bildungscampus.setEatingPlanList(eatingPlanList);
					locationList.add(bildungscampus);
				} if(index==2) {
					kuenzelsau.setEatingPlanList(eatingPlanList);
					locationList.add(kuenzelsau);
				}
				index++;
			}
			
			canteen.setCreated(createTimestamp());
			canteen.setLocationList(locationList);
			
			logger.debug("New CanteenPlan: " + canteen.toString());
			
			return canteen;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * Extracts the dish from the elements
	 * @param cols The elements of dishes
	 * @return Dish object
	 */
	private Dish extractDishFromElements(Elements cols) {
		try {
			String dish = null;
			String output = null;
			String studEur = null;
			String bedEur = null;
			String gastEur = null;
			
			int index = 0;
			for(Element value : cols) {
				switch(index) {
			    	case 0:
			    		dish = value.text();
			    		break;
			    	case 1:
			    		output = value.text();
			    		break;
			    	case 2:
			    		studEur = value.text();
			    		break;
			    	case 3:
			    		bedEur = value.text();
			    		break;
			    	case 4:
			    		gastEur = value.text();
			    		break;
			    	default:
			    		break;
				}
				index++;
			}
			return new Dish(dish, output, studEur, bedEur, gastEur);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * Extracts the date from the html tag
	 * @param text The content of the html tag
	 * @return The date or the input string based on result of the if condition
	 */
	private String extractDate(String text) {
		try {
			String[] stringArray = text.split(" ");
			
			if(stringArray[1] != null) {
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
