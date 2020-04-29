package com.hhn.vs.centerapp.splanservice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * URLs found in PPM APP
 *
 * @author fgreiner
 */
@Service
public class SplanService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final String coursesURL = "https://splan.hs-heilbronn.de/splan/rest/OrgGroupService/getSelectableOrgGroups/-1";
    private final String semestersURL = "https://splan.hs-heilbronn.de/splan/rest/PlanningGroupService/getPlanningGroupsForPlanningUnitAndOrgGroup/";
    private final String eventsURL = "https://splan.hs-heilbronn.de/splan/rest/TimetableService/getForPlanningUnitAndPlanningGroup/";

    public String getCourses() throws RestClientException {
        RestTemplate template = new RestTemplate();
        String jsonResponse = template.getForObject(coursesURL, String.class);
        return jsonResponse;
    }

    public String getSemesters(String planningUnit, String courseId) throws RestClientException {
        RestTemplate template = new RestTemplate();
        String jsonResponse = template.getForObject(semestersURL + planningUnit + "/" + courseId, String.class);
        return jsonResponse;
    }

    public String getEvents(String planningUnit, String semesterId) {
        RestTemplate template = new RestTemplate();
        String jsonResponse = template.getForObject(eventsURL + planningUnit + "/" + semesterId + "/false/-1", String.class);
        return jsonResponse;
    }

    public String getEventsByDate(String planningUnit, String semesterId, String date) {
        RestTemplate template = new RestTemplate();
        String jsonResponse = template.getForObject(eventsURL + planningUnit + "/" + semesterId + "/false/" + date, String.class);
        return jsonResponse;
    }
}
