package com.hhn.vs.centerapp.model;

import java.util.ArrayList;
import java.util.List;

public class FeedList {
    List<Feed> feedList;

    public FeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    public FeedList() {
        feedList = new ArrayList<>();
    }

    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }

    @Override
    public String toString() {
        return "FeedList{" +
                "feedList=" + feedList +
                '}';
    }
}
