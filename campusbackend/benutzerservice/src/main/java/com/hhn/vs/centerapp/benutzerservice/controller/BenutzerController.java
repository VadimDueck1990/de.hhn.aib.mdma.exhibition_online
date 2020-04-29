package com.hhn.vs.centerapp.benutzerservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hhn.vs.centerapp.benutzerservice.dto.BenutzerDTO;
import com.hhn.vs.centerapp.benutzerservice.service.BenutzerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.time.LocalDate;

/**
 * @author fgreiner
 */
@RestController()
@RequestMapping(value = "api/userRest")
@Api(value="User Management System")
public class BenutzerController {
	private static final Logger logger = LogManager.getLogger(BenutzerController.class);

    @Autowired
    BenutzerService benutzerService;

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
     * 
     * @param username
     * @param password
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets a specific user information")
    @RequestMapping(method = RequestMethod.GET, value = "/getUserInfo")
    public BenutzerDTO getUserInformation(@ApiParam(value = "Username of the user", required = true) @RequestParam("username") String username, 
    		@ApiParam(value = "Passwort of the user", required = true) @RequestParam("password") String password) {
        logger.info("received User call for: " + username);
        try {
            return benutzerService.fetchUserData(username, password);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }

    /**
     * 
     * @param username
     * @param password
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "Gets cached user information")
    @RequestMapping(method = RequestMethod.GET, value = "/getUserCached")
    public BenutzerDTO getCachedInfo(@ApiParam(value = "Username of the user", required = true) @RequestParam("username") String username, 
    		@ApiParam(value = "Passwort of the user", required = true) @RequestParam("password") String password) {
        logger.info("received User fetch for: " + username);
        BenutzerDTO user = new BenutzerDTO();
        user.setLdap(username);
        user.setLastname("Greiner");
        user.setFirstname("Friedrich");
        user.setProfile("student");
        user.setStudentNumber("201900");
        user.setStudyProgram("MIM");
        user.addEmail("fgreiner@stud.hs-heilbronn.de");
        user.addEmail("188192@stud.hs-heilbronn.de");
        user.setForwardEmails("fgreiner");
        user.setAccountExpiry(LocalDate.of(2019, 11, 1));
        user.setPasswordExpiry(LocalDate.of(2020, 6, 5));
        user.setActive("active");
        return user;
    }
}
