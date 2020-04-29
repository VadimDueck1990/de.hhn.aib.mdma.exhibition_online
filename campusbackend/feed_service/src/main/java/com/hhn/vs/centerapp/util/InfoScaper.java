package com.hhn.vs.centerapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.vs.centerapp.controller.FeedController;
import com.hhn.vs.centerapp.model.canteen.Canteen;
import com.hhn.vs.centerapp.model.meeting.MeetingPlan;
import com.hhn.vs.centerapp.model.semesterdate.SemesterDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class InfoScaper {

    private static final Logger logger = LogManager.getLogger(InfoScaper.class);

    private RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    public InfoScaper(){
        objectMapper = new ObjectMapper();
        restTemplate = new RestTemplate();
    }

    public enum Scrap {
        ALL,
        TODAY,
        SEVENDAYS,
        BEFORE_TODAY,
        TODAY_SONTHEIM,
        TODAY_CAMPUS,
        TODAY_KUENZELSAU
    }

    public Canteen getAllInfoFromCanteen(Scrap scrapSelection){

        if(scrapSelection == Scrap.ALL){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_CANTEEN_ALL, Canteen.class);
        }

        if(scrapSelection == Scrap.TODAY){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_CANTEEN_TODAY, Canteen.class);
        }

        if(scrapSelection == Scrap.TODAY_SONTHEIM){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_CANTEEN_TODAY_SONTHEIM, Canteen.class);
        }

        if(scrapSelection == Scrap.TODAY_CAMPUS){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_CANTEEN_TODAY_CAMPUS, Canteen.class);
        }

        if(scrapSelection == Scrap.TODAY_KUENZELSAU){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_CANTEEN_TODAY_KUENZELSAU, Canteen.class);
        }

        return null;
    }

    public MeetingPlan getAllInfoFromMeeting(Scrap scrapSelection){
        if(scrapSelection == Scrap.ALL){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_MEETING_ALL, MeetingPlan.class);
        }

        if(scrapSelection == Scrap.TODAY){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_MEETING_TODAY, MeetingPlan.class);
        }

        if(scrapSelection == Scrap.SEVENDAYS){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_MEETING_SEVEN_DAYS, MeetingPlan.class);
        }

        if(scrapSelection == Scrap.BEFORE_TODAY){
            return restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_MEETING_BEFORE_TODAY, MeetingPlan.class);
        }

        return null;
    }

    public List<SemesterDate> getAllInfoFromSemesterDates(Scrap scrapSelection){
        if(scrapSelection == Scrap.ALL){
            String jsonObject = restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_SEMESTER_DATES_ALL, String.class);
            try {
                List<SemesterDate> semesterDateList = objectMapper.readValue(jsonObject, new TypeReference<List<SemesterDate>>(){});
                return semesterDateList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        if(scrapSelection == Scrap.TODAY){
            String jsonObject = restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_SEMESTER_DATES_TODAY, String.class);
            try {
                List<SemesterDate> semesterDateList = objectMapper.readValue(jsonObject, new TypeReference<List<SemesterDate>>(){});
                return semesterDateList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        if(scrapSelection == Scrap.SEVENDAYS){
            String jsonObject = restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_SEMESTER_DATES_SEVEN_DAYS, String.class);
            try {
                List<SemesterDate> semesterDateList = objectMapper.readValue(jsonObject, new TypeReference<List<SemesterDate>>(){});
                return semesterDateList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        if(scrapSelection == Scrap.BEFORE_TODAY){
            String jsonObject = restTemplate.getForObject(Constants.Addresses.URL_ADDRESS_SEMESTER_DATES_BEFORE_TODAY, String.class);
            try {
                List<SemesterDate> semesterDateList = objectMapper.readValue(jsonObject, new TypeReference<List<SemesterDate>>(){});
                return semesterDateList;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }
}
