package com.hhn.vs.centerapp.splanservice.controller;

import com.hhn.vs.centerapp.splanservice.service.SplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fgreiner
 */
@RestController()
@RequestMapping(value = "api/splan")
@Api(value = "Splan Event System")
public class SplanController {
    //private static final Logger logger = LogManager.getLogger(SplanController.class);
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    SplanService splanService;

    /**
     * Method to test service availability.
     * In case this is the only working request something might be wrong with the service.
     *
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets status of service", response = String.class)
    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public void testAvailability() {
    }

    /**
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets splan Courses information")
    @RequestMapping(method = RequestMethod.GET, value = "/getCourses", produces = "application/json")
    public String getCourseInformation() {
        logger.info("received splan call.");
        try {
            return splanService.getCourses();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets splan Semesters information")
    @RequestMapping(method = RequestMethod.GET, value = "/getSemesters", produces = "application/json")
    public String getSemesterInformation(@ApiParam(value = "Planning Unit", required = true) @RequestParam("unit") String unit,
                                         @ApiParam(value = "Course Id", required = true) @RequestParam("course") String course
    ) {
        logger.info("received splan call.");
        try {
            return splanService.getSemesters(unit, course);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    /**
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets splan Events information")
    @RequestMapping(method = RequestMethod.GET, value = "/getEvents", produces = "application/json")
    public String getEventInformation(@ApiParam(value = "Planning Unit", required = true) @RequestParam("unit") String unit,
                                      @ApiParam(value = "Semester Id", required = true) @RequestParam("semester") String semester,
                                      @ApiParam(value = "Date (MM-dd-yyyy)") @RequestParam(value = "date", required = false) String dateString
    ) {
        logger.info("received splan call.");
        try {
            if(dateString != null) {
                DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                Date date = format.parse(dateString);
                return splanService.getEventsByDate(unit,semester,dateString);
            } else {
                return splanService.getEvents(unit, semester);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
