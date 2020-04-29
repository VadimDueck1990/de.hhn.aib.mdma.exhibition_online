package com.hhn.vs.centerapp.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hhn.vs.centerapp.model.Subscription;

@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
	
}
