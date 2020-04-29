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

import com.hhn.vs.centerapp.model.Canteen;
import com.hhn.vs.centerapp.model.EatingPlan;
import com.hhn.vs.centerapp.model.Location;
import com.hhn.vs.centerapp.scraper.CanteenPlanManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/canteenPlanRest")
@Api(value="Canteen Plan Management System")
public class CanteenPlanController {
	
	private static final Logger logger = LogManager.getLogger(CanteenPlanController.class);

	/**
	 * Gets a canteen objects
	 * @return A canteen object
	 */
	@ApiOperation(value = "Gets a canteen object")
	@RequestMapping(value = "/getAllData", method = RequestMethod.GET)
	@CrossOrigin
	public Canteen getAllData(){
		logger.info("/getAllData requested");
		
		return CanteenPlanManager.getInstance().getCanteen();
	}
	
	/**
	 * Gets a list of available canteen object today
	 * @return A list of canteen object today
	 */
	@ApiOperation(value = "Gets a list of available canteen today")
	@RequestMapping(value = "/getAllDataToday", method = RequestMethod.GET)
	@CrossOrigin
	public Canteen getAllCanteenPlanToday() {
		logger.info("/getAllDataToday requested");
		
		Canteen newCanteen = new Canteen();
		Canteen currentCanteen = CanteenPlanManager.getInstance().getCanteen();
		
		List<Location> locationList = currentCanteen.getLocationList();
		
		List<Location> newLocationList = new ArrayList<Location>();
		
		for(Location location : locationList) {
			List<EatingPlan> eatingPlanList = location.getEatingPlanList();
			List<EatingPlan> newEatingPlan  = new ArrayList<EatingPlan>();
			
			for(EatingPlan eatingPlan : eatingPlanList) {
				
				try {
					LocalDate localDate = LocalDate.now();//For reference
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
					String formattedString = localDate.format(formatter);
					
					if(formattedString.equalsIgnoreCase(eatingPlan.getDate())) {
						newEatingPlan.add(eatingPlan);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			Location newLocation = new Location();
			newLocation.setName(location.getName());
			newLocation.setEatingPlanList(newEatingPlan);
			
			newLocationList.add(newLocation);
		}
		
		newCanteen.setLocationList(newLocationList);
		newCanteen.setCreated(currentCanteen.getCreated());
		
		return newCanteen;
	}
	
	/**
	 * Gets a list of available canteen object today
	 * @return A list of canteen object today
	 */
	@ApiOperation(value = "Gets a list of available canteen from sontheim today")
	@RequestMapping(value = "/getAllDataFromSontheimToday", method = RequestMethod.GET)
	@CrossOrigin
	public Canteen getAllCanteenPlanFromSontheimToday() {
		logger.info("/getAllDataFromSontheimToday requested");

		Canteen newCanteen = new Canteen();
		Canteen currentCanteen = CanteenPlanManager.getInstance().getCanteen();
		
		List<Location> locationList = currentCanteen.getLocationList();
		
		List<Location> newLocationList = new ArrayList<Location>();
		
		for(Location location : locationList) {

			if(location.getName().equalsIgnoreCase("Sontheim")){

				List<EatingPlan> eatingPlanList = location.getEatingPlanList();
				List<EatingPlan> newEatingPlan  = new ArrayList<EatingPlan>();
				
				for(EatingPlan eatingPlan : eatingPlanList) {
					
					try {
						LocalDate localDate = LocalDate.now();//For reference
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedString = localDate.format(formatter);
						
						if(formattedString.equalsIgnoreCase(eatingPlan.getDate())) {
							newEatingPlan.add(eatingPlan);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				
				Location newLocation = new Location();
				newLocation.setName(location.getName());
				newLocation.setEatingPlanList(newEatingPlan);
				
				newLocationList.add(newLocation);
			}
		}
		
		newCanteen.setLocationList(newLocationList);
		newCanteen.setCreated(currentCanteen.getCreated());
		
		return newCanteen;
	}

	/**
	 * Gets a list of available canteen object today
	 * @return A list of canteen object today
	 */
	@ApiOperation(value = "Gets a list of available canteen from Bildungscampus today")
	@RequestMapping(value = "/getAllDataFromBildungscampusToday", method = RequestMethod.GET)
	@CrossOrigin
	public Canteen getAllCanteenPlanFromBildungscampusToday() {
		logger.info("/getAllDataFromBildungscampusToday requested");

		Canteen newCanteen = new Canteen();
		Canteen currentCanteen = CanteenPlanManager.getInstance().getCanteen();
		
		List<Location> locationList = currentCanteen.getLocationList();
		
		List<Location> newLocationList = new ArrayList<Location>();
		
		for(Location location : locationList) {

			if(location.getName().equalsIgnoreCase("Bildungscampus")){

				List<EatingPlan> eatingPlanList = location.getEatingPlanList();
				List<EatingPlan> newEatingPlan  = new ArrayList<EatingPlan>();
				
				for(EatingPlan eatingPlan : eatingPlanList) {
					
					try {
						LocalDate localDate = LocalDate.now();//For reference
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedString = localDate.format(formatter);
						
						if(formattedString.equalsIgnoreCase(eatingPlan.getDate())) {
							newEatingPlan.add(eatingPlan);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				
				Location newLocation = new Location();
				newLocation.setName(location.getName());
				newLocation.setEatingPlanList(newEatingPlan);
				
				newLocationList.add(newLocation);
			}
		}
		
		newCanteen.setLocationList(newLocationList);
		newCanteen.setCreated(currentCanteen.getCreated());
		
		return newCanteen;
	}

	/**
	 * Gets a list of available canteen object today
	 * @return A list of canteen object today
	 */
	@ApiOperation(value = "Gets a list of available canteen from kuenzelsau today")
	@RequestMapping(value = "/getAllDataFromKuenzelsauToday", method = RequestMethod.GET)
	@CrossOrigin
	public Canteen getAllCanteenPlanFromKuenzelsauToday() {
		logger.info("/getAllDataFromKuenzelsauToday requested");

		Canteen newCanteen = new Canteen();
		Canteen currentCanteen = CanteenPlanManager.getInstance().getCanteen();
		
		List<Location> locationList = currentCanteen.getLocationList();
		
		List<Location> newLocationList = new ArrayList<Location>();
		
		for(Location location : locationList) {

			if(location.getName().equalsIgnoreCase("Kuenzelsau")){
				
				List<EatingPlan> eatingPlanList = location.getEatingPlanList();
				List<EatingPlan> newEatingPlan  = new ArrayList<EatingPlan>();
				
				for(EatingPlan eatingPlan : eatingPlanList) {
					
					try {
						LocalDate localDate = LocalDate.now();//For reference
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
						String formattedString = localDate.format(formatter);
						
						if(formattedString.equalsIgnoreCase(eatingPlan.getDate())) {
							newEatingPlan.add(eatingPlan);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				
				Location newLocation = new Location();
				newLocation.setName(location.getName());
				newLocation.setEatingPlanList(newEatingPlan);
				
				newLocationList.add(newLocation);
			}
		}
		
		newCanteen.setLocationList(newLocationList);
		newCanteen.setCreated(currentCanteen.getCreated());
		
		return newCanteen;
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
}
