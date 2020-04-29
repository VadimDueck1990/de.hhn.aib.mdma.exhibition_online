package com.hhn.vs.centerapp.benutzerservice.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;

import com.hhn.vs.centerapp.benutzerservice.dto.BenutzerDTO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * interact with the HHN Benutzerverwaltung.
 *
 * @author fgreiner
 */
@Service
public class BenutzerService {

    // The page userDetails are usually displayed at
    private final String userProfileURL = "https://benutzer.hs-heilbronn.de/manager/user.php";

    private static final Logger logger = LogManager.getLogger(BenutzerService.class);

    /**
     * This method fetches the profile Page, parsing the userdata from html document to BenutzerDTO
     * Http-Basic-Authentication is used to access the Data on the server.
     *
     * Used Librarys:   - Spring RestTemplate: Rest calls
     *                  - JSoup: parsing html
     *
     * @param ldap Username
     * @param pwd Password
     * @return BenutzerDTO
     */
    public BenutzerDTO fetchUserData(String ldap, String pwd) {
        // build Authentication String
        String encoding = Base64.getEncoder().encodeToString((ldap + ":" + pwd).getBytes());

        // get html Page using authentication
        RestTemplate template = new RestTemplate();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encoding);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<String> response = template.exchange(userProfileURL, HttpMethod.POST, request, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            logger.info("Profile page loaded successfull");
        } else {
            logger.warn("Something unusually happend loading Details for: " + ldap);
        }
        // read html
        Document doc = Jsoup.parse(response.getBody());
        ArrayList<String> data = new ArrayList<String>();

        // copy relevant values
        for (Element element : doc.select("td")) {
            if (element.childNodes().size() == 1) {
                data.add(element.html());
            }
        }

        // set Values to DTO
        BenutzerDTO user = new BenutzerDTO();
        user.setLdap(ldap);
        int index = 0;
        // find lastname skipping leading content
        while (!data.get(index).startsWith("Nachname")) {
            index++;
        }
        index++;
        // lastname
        user.setLastname(data.get(index));
        index += 2;
        // firstname
        user.setFirstname(data.get(index));
        index += 2;
        // Profil
        user.setProfile(data.get(index));
        index += 2;
        // Number
        user.setStudentNumber(data.get(index));
        index += 2;
        // Studiengang:
        user.setStudyProgram(data.get(index));
        index += 2;
        // get all Emails:
        while (!data.get(index).startsWith("E-Mail-Weiterleitung")) {
            user.addEmail(data.get(index));
            index++;
        }
        index++;
        // Forward
        user.setForwardEmails(data.get(index));
        index += 2;
        // Account expiry
        user.setAccountExpiry(getLocalDate(data.get(index)));
        index += 2;
        // Password expiry
        user.setPasswordExpiry(getLocalDate(data.get(index)));
        index += 2;
        // Active Status
        user.setActive(data.get(index));

        return user;
    }

    private LocalDate getLocalDate(String datestring) {
        String[] split = datestring.split("\\.");
        int day = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int year = Integer.parseInt(split[2]);
        return LocalDate.of(year, month, day);
    }
}
