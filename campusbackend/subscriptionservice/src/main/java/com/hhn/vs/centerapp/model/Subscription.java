package com.hhn.vs.centerapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

@RedisHash("Subscription")
public class Subscription {
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private @Id String id;	
	
	@ApiModelProperty(accessMode = AccessMode.READ_WRITE, required = true)
	private String name;
	
	@ApiModelProperty(accessMode = AccessMode.READ_WRITE, required = true)
	private String restURL;
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private String created;
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private String lastUpdate;
	
	public Subscription(String name, String restURL, String created, String lastUpdate) {
		this.name = name;
		this.restURL = restURL;
		this.created = created;
		this.lastUpdate = lastUpdate;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRestURL() {
		return restURL;
	}
	
	public String getCreated() {
		return created;
	}
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setRestURL(String restURL) {
		this.restURL = restURL;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Override
	public String toString() {
		return "Subscription [id=" + id + ", name=" + name + ", restURL=" + restURL + ", created=" + created
				+ ", lastUpdate=" + lastUpdate + "]";
	}
}
