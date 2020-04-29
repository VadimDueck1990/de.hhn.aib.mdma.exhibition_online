package com.hhn.vs.centerapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhn.vs.centerapp.model.Feed;
import com.hhn.vs.centerapp.model.FeedList;
import com.hhn.vs.centerapp.model.Request;
import com.hhn.vs.centerapp.model.canteen.Canteen;
import com.hhn.vs.centerapp.model.canteen.Dish;
import com.hhn.vs.centerapp.model.canteen.EatingPlan;
import com.hhn.vs.centerapp.model.canteen.Location;
import com.hhn.vs.centerapp.model.meeting.Meeting;
import com.hhn.vs.centerapp.model.meeting.MeetingPlan;
import com.hhn.vs.centerapp.model.semesterdate.SemesterDate;
import com.hhn.vs.centerapp.util.InfoScaper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/feedRest")
@Api(value="Feed Management System")
public class FeedController {

    private static final Logger logger = LogManager.getLogger(FeedController.class);

    private InfoScaper infoScaper = new InfoScaper();
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Gets all information about the subscription services
     * @param request The requestbody for selection of subscription service information
     * @return A list of all requested information
     */
    @ApiOperation(value = "Gets all information about the subscription services")
    @RequestMapping(value = "/getAllInformation", method = RequestMethod.POST)
    @CrossOrigin
    public FeedList getAllInformation(@ApiParam(value = "The requestbody for selection of subscription service information", required = true)
                                          @RequestBody Request request){
        logger.info("/getAllInformation requested");

        List<String> subList = request.getSubscriptionList();
        FeedList feedList = new FeedList();

        for(String sub : subList){

            //Is there a Mensaplan in the subscription part
            if(sub.equalsIgnoreCase("Mensaplan")){
                logger.info("Get Information about mensaplan");
                Canteen newCanteen = infoScaper.getAllInfoFromCanteen(InfoScaper.Scrap.ALL);

                if(newCanteen != null){
                    List<Location> locationList = newCanteen.getLocationList();

                    for(Location location : locationList){
                        if(location.getName().equalsIgnoreCase(request.getCanteenLocation())){
                            List<EatingPlan> eatingPlan = location.getEatingPlanList();
                            Feed feedCanteen = getFeedFromCanteen(eatingPlan);

                            List<Feed> newFeedList = feedList.getFeedList();
                            newFeedList.add(feedCanteen);

                            feedList.setFeedList(newFeedList);
                        }
                    }
                }
            }

            //Is there a Veranstaltungen in the subscription part
            if(sub.equalsIgnoreCase("Veranstaltungen")){
                logger.info("Get Information about meetings");
                MeetingPlan newMeetingPlan = infoScaper.getAllInfoFromMeeting(InfoScaper.Scrap.ALL);

                if(newMeetingPlan != null){
                    List<Meeting> meetingList = newMeetingPlan.getMeetingList();

                    for(Meeting meeting : meetingList){
                        Feed feedMeeting = getFeedFromMeeting(meeting);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedMeeting);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Semesterplan in the subscription part
            if(sub.equalsIgnoreCase("Semesterplan")){
                logger.info("Get Information about semester dates");
                List<SemesterDate> semesterDatesList = infoScaper.getAllInfoFromSemesterDates(InfoScaper.Scrap.ALL);

                if(semesterDatesList != null){
                    for(SemesterDate semesterDate : semesterDatesList){
                        Feed feedSemesterDate = getFeedFromSemesterDate(semesterDate);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedSemesterDate);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Gruppentreffen in the subscription part
            if(sub.equalsIgnoreCase("Gruppentreffen")){
                logger.info("Get Information about groupe meetings");
                //Empty
            }
        }

        return feedList;
    }

    /**
     * Gets all information about the subscription services today
     * @param request The requestbody for selection of subscription service information
     * @return A list of all requested information
     */
    @ApiOperation(value = "Gets all information about the subscription services today")
    @RequestMapping(value = "/getAllInformationToday", method = RequestMethod.POST)
    @CrossOrigin
    public FeedList getAllInformationToday(@ApiParam(value = "The requestbody for selection of subscription service information", required = true)
                                               @RequestBody Request request){
        logger.info("/getAllInformationToday requested");

        List<String> subList = request.getSubscriptionList();
        FeedList feedList = new FeedList();

        for(String sub : subList){

            //Is there a Mensaplan in the subscription part
            if(sub.equalsIgnoreCase("Mensaplan")){
                logger.info("Get Information about mensaplan");
                Canteen newCanteen = infoScaper.getAllInfoFromCanteen(InfoScaper.Scrap.TODAY);

                if(newCanteen != null){
                    List<Location> locationList = newCanteen.getLocationList();

                    for(Location location : locationList){
                        if(location.getName().equalsIgnoreCase(request.getCanteenLocation())){
                            List<EatingPlan> eatingPlan = location.getEatingPlanList();
                            Feed feedCanteen = getFeedFromCanteen(eatingPlan);

                            List<Feed> newFeedList = feedList.getFeedList();
                            newFeedList.add(feedCanteen);

                            feedList.setFeedList(newFeedList);
                        }
                    }
                }
            }

            //Is there a Veranstaltungen in the subscription part
            if(sub.equalsIgnoreCase("Veranstaltungen")){
                logger.info("Get Information about meetings");
                MeetingPlan newMeetingPlan = infoScaper.getAllInfoFromMeeting(InfoScaper.Scrap.TODAY);

                if(newMeetingPlan != null){
                    List<Meeting> meetingList = newMeetingPlan.getMeetingList();

                    for(Meeting meeting : meetingList){
                        Feed feedMeeting = getFeedFromMeeting(meeting);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedMeeting);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Semesterplan in the subscription part
            if(sub.equalsIgnoreCase("Semesterplan")){
                logger.info("Get Information about semester dates");
                List<SemesterDate> semesterDatesList = infoScaper.getAllInfoFromSemesterDates(InfoScaper.Scrap.TODAY);

                if(semesterDatesList != null){
                    for(SemesterDate semesterDate : semesterDatesList){
                        Feed feedSemesterDate = getFeedFromSemesterDate(semesterDate);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedSemesterDate);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Gruppentreffen in the subscription part
            if(sub.equalsIgnoreCase("Gruppentreffen")){
                logger.info("Get Information about groupe meetings");
                //Empty
            }
        }

        return feedList;
    }



    /**
     * Gets all information about the subscription services last seven days
     * @param request The requestbody for selection of subscription service information
     * @return A list of all requested information
     */
    @ApiOperation(value = "Gets all information about the subscription services last seven days")
    @RequestMapping(value = "/getAllInformationLastSevenDays", method = RequestMethod.POST)
    @CrossOrigin
    public FeedList getAllInformationLastSevenDays(@ApiParam(value = "The requestbody for selection of subscription service information", required = true)
                                                       @RequestBody Request request){
        logger.info("/getAllInformationLastSevenDays requested");

        List<String> subList = request.getSubscriptionList();
        FeedList feedList = new FeedList();

        for(String sub : subList){

            //Is there a Mensaplan in the subscription part
            if(sub.equalsIgnoreCase("Mensaplan")){
                logger.info("Get Information about mensaplan");
                Canteen newCanteen = infoScaper.getAllInfoFromCanteen(InfoScaper.Scrap.TODAY);

                if(newCanteen != null){
                    List<Location> locationList = newCanteen.getLocationList();

                    for(Location location : locationList){
                        if(location.getName().equalsIgnoreCase(request.getCanteenLocation())){
                            List<EatingPlan> eatingPlan = location.getEatingPlanList();
                            Feed feedCanteen = getFeedFromCanteen(eatingPlan);

                            List<Feed> newFeedList = feedList.getFeedList();
                            newFeedList.add(feedCanteen);

                            feedList.setFeedList(newFeedList);
                        }
                    }
                }
            }

            //Is there a Veranstaltungen in the subscription part
            if(sub.equalsIgnoreCase("Veranstaltungen")){
                logger.info("Get Information about meetings");
                MeetingPlan newMeetingPlan = infoScaper.getAllInfoFromMeeting(InfoScaper.Scrap.SEVENDAYS);

                if(newMeetingPlan != null){
                    List<Meeting> meetingList = newMeetingPlan.getMeetingList();

                    for(Meeting meeting : meetingList){
                        Feed feedMeeting = getFeedFromMeeting(meeting);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedMeeting);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Semesterplan in the subscription part
            if(sub.equalsIgnoreCase("Semesterplan")){
                logger.info("Get Information about semester dates");
                List<SemesterDate> semesterDatesList = infoScaper.getAllInfoFromSemesterDates(InfoScaper.Scrap.SEVENDAYS);

                if(semesterDatesList != null){
                    for(SemesterDate semesterDate : semesterDatesList){
                        Feed feedSemesterDate = getFeedFromSemesterDate(semesterDate);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedSemesterDate);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Gruppentreffen in the subscription part
            if(sub.equalsIgnoreCase("Gruppentreffen")){
                logger.info("Get Information about groupe meetings");
                //Empty
            }
        }

        return feedList;
    }

    /**
     * Gets all information about the subscription services before today
     * @param request The requestbody for selection of subscription service information
     * @return A list of all requested information
     */
    @ApiOperation(value = "Gets all information about the subscription services before today")
    @RequestMapping(value = "/getAllInformationBeforeToday", method = RequestMethod.POST)
    @CrossOrigin
    public FeedList getAllInformationBeforeToday(@ApiParam(value = "The requestbody for selection of subscription service information", required = true)
                                                   @RequestBody Request request){
        logger.info("/getAllInformationBeforeToday requested");

        List<String> subList = request.getSubscriptionList();
        FeedList feedList = new FeedList();

        for(String sub : subList){

            //Is there a Mensaplan in the subscription part
            if(sub.equalsIgnoreCase("Mensaplan")){
                logger.info("Get Information about mensaplan");
                Canteen newCanteen = infoScaper.getAllInfoFromCanteen(InfoScaper.Scrap.TODAY);

                if(newCanteen != null){
                    List<Location> locationList = newCanteen.getLocationList();

                    for(Location location : locationList){
                        if(location.getName().equalsIgnoreCase(request.getCanteenLocation())){
                            List<EatingPlan> eatingPlan = location.getEatingPlanList();
                            Feed feedCanteen = getFeedFromCanteen(eatingPlan);

                            List<Feed> newFeedList = feedList.getFeedList();
                            newFeedList.add(feedCanteen);

                            feedList.setFeedList(newFeedList);
                        }
                    }
                }
            }

            //Is there a Veranstaltungen in the subscription part
            if(sub.equalsIgnoreCase("Veranstaltungen")){
                logger.info("Get Information about meetings");
                MeetingPlan newMeetingPlan = infoScaper.getAllInfoFromMeeting(InfoScaper.Scrap.BEFORE_TODAY);

                if(newMeetingPlan != null){
                    List<Meeting> meetingList = newMeetingPlan.getMeetingList();

                    for(Meeting meeting : meetingList){
                        Feed feedMeeting = getFeedFromMeeting(meeting);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedMeeting);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Semesterplan in the subscription part
            if(sub.equalsIgnoreCase("Semesterplan")){
                logger.info("Get Information about semester dates");
                List<SemesterDate> semesterDatesList = infoScaper.getAllInfoFromSemesterDates(InfoScaper.Scrap.BEFORE_TODAY);

                if(semesterDatesList != null){
                    for(SemesterDate semesterDate : semesterDatesList){
                        Feed feedSemesterDate = getFeedFromSemesterDate(semesterDate);

                        List<Feed> newFeedList = feedList.getFeedList();
                        newFeedList.add(feedSemesterDate);

                        feedList.setFeedList(newFeedList);
                    }
                }
            }

            //Is there a Gruppentreffen in the subscription part
            if(sub.equalsIgnoreCase("Gruppentreffen")){
                logger.info("Get Information about groupe meetings");
                //Empty
            }
        }

        return feedList;
    }

    /**
     * Gets the status
     */
    @ApiOperation(value = "Gets status of service", response = String.class)
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @CrossOrigin
    public void testAvailability() {
        InfoScaper infoScaper = new InfoScaper();
        Canteen canteen = infoScaper.getAllInfoFromCanteen(InfoScaper.Scrap.ALL);
        System.out.println(canteen.toString());
    }

    private Feed getFeedFromCanteen(List<EatingPlan> eatingPlanList){

        for(EatingPlan eatingPlan : eatingPlanList){
            List<String> descriptionList = new ArrayList<>();
            List<Dish> dishList = eatingPlan.getDishList();
            if(dishList != null){
                for(Dish dish : dishList){
                    try {
                        String jsonString = objectMapper.writeValueAsString(dish);
                        descriptionList.add(jsonString);
                    } catch(Exception e){
                        logger.error(e.toString());
                    }
                }
            }

            return new Feed("Mensagericht heute", descriptionList.toString(), eatingPlan.getDate(), "Mensaplan");
        }
        return null;
    }

    private Feed getFeedFromMeeting(Meeting meeting){
        String description = meeting.getTitle() + " || " + meeting.getTime() + " || " + meeting.getLocation();
        return new Feed(meeting.getTitle(), description, meeting.getDate(), "Veranstaltungen");
    }

    private Feed getFeedFromSemesterDate(SemesterDate semesterDate) {
        return new Feed(semesterDate.getTitle(), semesterDate.getDescription(), semesterDate.getDateBegin(), "Semesterplan");
    }
}
