package com.hhn.vs.centerapp.model;

import java.util.List;

public class Request {
    String username;
    String canteenLocation;
    List<String> subscriptionList;

    public Request(String username, String canteenLocation, String dayType, List<String> subscriptionList) {
        this.username = username;
        this.canteenLocation = canteenLocation;
        this.subscriptionList = subscriptionList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCanteenLocation() {
        return canteenLocation;
    }

    public void setCanteenLocation(String canteenLocation) {
        this.canteenLocation = canteenLocation;
    }

    public List<String> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<String> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }

    @Override
    public String toString() {
        return "Request{" +
                "username='" + username + '\'' +
                ", canteenLocation='" + canteenLocation + '\'' +
                ", subscriptionList=" + subscriptionList +
                '}';
    }
}
