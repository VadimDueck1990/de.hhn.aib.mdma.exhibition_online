package com.hhn.vs.centerapp.benutzerservice.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to transfer Userdata as JSON Object.
 *
 * @author fgreiner
 */
public class BenutzerDTO {
    private String ldap;
    private String lastname;
    private String firstname;
    private String profile;
    private String studentNumber;
    private String studyProgram;
    private List<String> emails;
    private String forwardEmails;
    private LocalDate accountExpiry;
    private LocalDate passwordExpiry;
    private String active;

    public BenutzerDTO() {
        emails = new ArrayList<String>();
    }

    @Override
    public String toString() {
        String s = "Name: " + firstname + " " + lastname
                + "\nNumber: " + studentNumber
                + "\nRolle: " + profile
                + "\nFach: " + studyProgram
                + "\nPWDExpiry: " + passwordExpiry
                +"\nEmails: ";
        for (String mail : emails) {
            s+=mail + "\n";
        }
        s+= "Status: " + this.active
                +"...";
        return s;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(String studyProgram) {
        this.studyProgram = studyProgram;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public boolean addEmail(String email) {
        return this.emails.add(email);
    }

    public String getForwardEmails() {
        return forwardEmails;
    }

    public void setForwardEmails(String forwardEmails) {
        this.forwardEmails = forwardEmails;
    }

    public LocalDate getAccountExpiry() {
        return accountExpiry;
    }

    public void setAccountExpiry(LocalDate accountExpiry) {
        this.accountExpiry = accountExpiry;
    }

    public LocalDate getPasswordExpiry() {
        return passwordExpiry;
    }

    public void setPasswordExpiry(LocalDate passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive() {
        return active;
    }

    public String getLdap() {
        return ldap;
    }

    public void setLdap(String ldap) {
        this.ldap = ldap;
    }
}
