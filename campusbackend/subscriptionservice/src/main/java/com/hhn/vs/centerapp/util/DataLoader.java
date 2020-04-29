package com.hhn.vs.centerapp.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hhn.vs.centerapp.model.Subscription;
import com.hhn.vs.centerapp.repo.SubscriptionRepository;

@Component
public class DataLoader {
	private static final Logger logger = LogManager.getLogger(DataLoader.class);
	private SubscriptionRepository subscriptionRepository;

	 @Autowired
	 public DataLoader(SubscriptionRepository subscriptionRepository) {
		 this.subscriptionRepository = subscriptionRepository;
		 LoadUsers();
	 }
	 
	 private void LoadUsers() {
		 logger.debug("Load dummydata");
		 subscriptionRepository.deleteAll();
		 subscriptionRepository.save(new Subscription("Mensaplan", null, null, null));
		 subscriptionRepository.save(new Subscription("Veranstaltungen", null, null, null));
		 subscriptionRepository.save(new Subscription("Semesterplan", null, null, null));
		 subscriptionRepository.save(new Subscription("Gruppentreffen", null, null, null));
	 }
}
