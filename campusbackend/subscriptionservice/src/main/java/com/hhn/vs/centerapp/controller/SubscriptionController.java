package com.hhn.vs.centerapp.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hhn.vs.centerapp.model.Subscription;
import com.hhn.vs.centerapp.model.Timestamp;
import com.hhn.vs.centerapp.repo.SubscriptionRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "api/subscriptionRest")
@Api(value="Subscription Management System")
public class SubscriptionController {
	
	private static final Logger logger = LogManager.getLogger(SubscriptionController.class);
	
	private Timestamp lastUpdateTimestamp = new Timestamp("");
	
	@Autowired
	SubscriptionRepository subscriptionRepository;
	
	/**
	 * Gets all data from the restful service
	 * @return A list of all subscription objects
	 */
	@ApiOperation(value = "Gets a list of available subscription")
	@RequestMapping(value = "/getAllData", method = RequestMethod.GET)
	@CrossOrigin
    public List<Subscription> getAllData() {
		logger.info("/getAllData requested");
		updateLastUpdateTimestamp();
        
		//Create new list for objects
		List<Subscription> subscriptions = new ArrayList<>();
		
		logger.debug("Get all objects from redis database");
		
		//Get all objects from redis database
		subscriptionRepository.findAll().forEach(subscriptions::add);
		
		logger.info("/getAllData responded with list of " + subscriptions.size() + " objects");
		
		if(subscriptions.isEmpty()) {
			return null;
		}
		
		return subscriptions;
    }
	
	/**
	 * Creates a new subscription object on the database
	 * @param subscription The given subscription object
	 * @return Created subscription object from the database
	 */
	@ApiOperation(value = "Creates a new subscription object")
	@RequestMapping(value = "/createSubscription", method = RequestMethod.POST)
	@CrossOrigin
	public Subscription createSubscription(@RequestBody Subscription subscription) {
		logger.info("/createSubscription requested");
		updateLastUpdateTimestamp();
		
		//Create new id for new object
		String id = UUID.randomUUID().toString();
		subscription.setId(id);
		subscription.setCreated(createTimestamp());
		subscription.setLastUpdate(createTimestamp());
		
		logger.debug("New subscription object: " + subscription.toString());
		
		//Insert new object into the redis database
		subscriptionRepository.save(subscription);
		
		logger.info(subscriptionRepository.findById(id).toString());
		
		return subscriptionRepository.findById(id).get();
	}
	
	/**
	 * Updates a subscription object
	 * @param id The id of the subscription object
	 * @param subscription Updated subscription object from the database
	 */
	@ApiOperation(value = "Updates a exisiting subscription object")
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/updateSubscription/{id}", method = RequestMethod.PUT)
	@CrossOrigin
	public void updateSubscription(@ApiParam(value = "Subscription Id to update subscription object", required = true) @PathVariable(value = "id") String id,
			@RequestBody Subscription subscription) {
		logger.info("/updateSubscription/{id} requested");
		updateLastUpdateTimestamp();
		
		try {
			//Get specific object from redis database based on id
			Subscription updateSubscription = subscriptionRepository.findById(id).get();
			
			subscription.setId(updateSubscription.getId());
			subscription.setLastUpdate(createTimestamp());
			subscription.setCreated(updateSubscription.getCreated());
			
			logger.debug("Update: " + subscription.toString());
			
			//Insert updated object into the redis database
			subscriptionRepository.save(subscription);
			
			logger.info("/updateSubscription request done");
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
	}
	
	/**
	 * Deletes a subscription object
	 * @param id The id of the subscription object
	 */
	@ApiOperation(value = "Deletes a exisiting subscription object based on id")
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/deleteSubscription/{id}", method = RequestMethod.DELETE)
	@CrossOrigin
	public void deleteSubscription(@ApiParam(value = "Subscription Id to delete subscription object", required = true) @PathVariable(value = "id") String id) {
		logger.info("/deleteSubscription/"+ id +" requested");
		updateLastUpdateTimestamp();
		
		try {
			Subscription subscription = subscriptionRepository.findById(id).get();
			logger.debug("Delete: " + subscription.toString());
			
			//Get specific object from redis database based on id
			subscriptionRepository.deleteById(id);
			
			logger.info("/deleteSubscription/{id} request done");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Deletes all data from the restful service
	 */
	@ApiOperation(value = "Deletes all data of available subscription")
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/deleteAllData", method = RequestMethod.DELETE)
	@CrossOrigin
    public void deleteAllData() {
		logger.info("/deleteAllData requested");
		updateLastUpdateTimestamp();
		
		try {
			logger.debug("Deletes all objects from redis database");
			subscriptionRepository.deleteAll();
			
			logger.info("/getAllData request done");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    }
	
	/**
	 * Gets a timestamp of the last service update
	 * @return Timestamp of the last service update
	 */
	@ApiOperation(value = "Gets a last update timestamp", response = Timestamp.class)
	@RequestMapping(value = "/lastServiceUpdate", method = RequestMethod.GET)
	@CrossOrigin
	public Timestamp lastUpdate() {
		logger.info("/lastUpdate requested");
		return lastUpdateTimestamp;
	}
	
	/**
	 * Gets status of the service
	 */
	@ApiOperation(value = "Gets status of service", response = String.class)
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@CrossOrigin
    public void testAvailability() {
    }
	
	/**
	 * Creates a timestamp based on ISO8601 of the current date and time
	 * @return Timestamp string
	 */
	private String createTimestamp() {
		logger.debug("Create a ISO8601 timestamp");
		
		TimeZone tz = TimeZone.getTimeZone("Europe/Berlin");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(new Date());
	}
	
	/**
	 * Updates the lastUpdateTimestamp
	 */
	private void updateLastUpdateTimestamp() {
		lastUpdateTimestamp.setTimestamp(createTimestamp());
	}
}
