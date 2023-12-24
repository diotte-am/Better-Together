package edu.northeastern.team_11.BetterTogether.Tabs;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import retrofit2.http.Query;

public class BTUser implements Serializable {
    private String birthday;
    private String city;
    private String country;
    private String email;
    private String firstName;
    private String lastName;
    private String pronouns;
    private String userName;
    private String password;
    private String bio;
    private String joined;
    private String id;
    private Integer medStreakCount;
    private Integer affStreakCount;
    private Integer yogaStreakCount;
    private Integer journStreakCount;
    private String lastMedActivityDate;
    private String lastAffActivityDate;
    private String lastYogaActivityDate;
    private String lastJournActivityDate;


    public BTUser() {
        // Default constructor used for DataSnapshots
    }

    public BTUser(BTUser btUser){
        this.id = btUser.getId();
        this.birthday = btUser.getBirthday();
        this.city = btUser.getCity();
        this.country = btUser.getCountry();
        this.email = btUser.getEmail();
        this.firstName = btUser.getFirstName();
        this.lastName = btUser.getLastName();
        this.pronouns = btUser.getPronouns();
        this.userName = btUser.getUserName();
        this.password = btUser.getPassword();
        this.bio = btUser.getBio();
        this.joined = btUser.getJoined();
        this.medStreakCount = btUser.getMedStreakCount();
        this.affStreakCount = btUser.getAffStreakCount();
        this.yogaStreakCount = btUser.getYogaStreakCount();
        this.journStreakCount = btUser.getJournStreakCount();
        this.lastMedActivityDate = btUser.getLastMedActivityDate();
        this.lastAffActivityDate = btUser.getLastAffActivityDate();
        this.lastYogaActivityDate = btUser.getLastYogaActivityDate();
        this.lastJournActivityDate = btUser.getLastJournActivityDate();

    }

    @Override
    public boolean equals(Object user) {
        if (this == user) {
            return true;
        }
        if (user == null || getClass() != user.getClass()){
            return false;
        }
        BTUser btUser = (BTUser) user;
        return id == btUser.id &&
                birthday == btUser.birthday &&
                city == btUser.city &&
                country == btUser.country &&
                email == btUser.email &&
                firstName == btUser.firstName &&
                lastName == btUser.lastName &&
                pronouns == btUser.pronouns &&
                userName == btUser.userName &&
                password == btUser.password &&
                bio == btUser.bio &&
                joined == btUser.joined &&
                medStreakCount == btUser.getMedStreakCount() &&
                affStreakCount == btUser.getAffStreakCount() &&
                yogaStreakCount == btUser.getYogaStreakCount() &&
                journStreakCount == btUser.getJournStreakCount() &&
                lastMedActivityDate == btUser.getLastMedActivityDate() &&
                lastAffActivityDate == btUser.getLastAffActivityDate() &&
                lastYogaActivityDate == btUser.getLastYogaActivityDate() &&
                lastJournActivityDate == btUser.getLastJournActivityDate();

    }

    public BTUser(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setPassword(String password){ this.password = password;}

    public void setBio(String bio) { this.bio = bio;}

    public String getPassword() {return password; }
    public String getJoined() {return joined ; }
    public void setJoined(String joined) {this.joined = joined;}
    public String getBio() {return bio; }
    public Integer getMedStreakCount() {
        return medStreakCount;
    }

    public void setMedStreakCount(Integer medStreakCount) {
        this.medStreakCount = medStreakCount;
    }

    public Integer getAffStreakCount() {
        return affStreakCount;
    }

    public void setAffStreakCount(Integer affStreakCount) {
        this.affStreakCount = affStreakCount;
    }

    public Integer getYogaStreakCount() {
        return yogaStreakCount;
    }

    public void setYogaStreakCount(Integer yogaStreakCount) {
        this.yogaStreakCount = yogaStreakCount;
    }
    public Integer getJournStreakCount() {
        return journStreakCount;
    }
    public void setJournStreakCount(Integer journStreakCount) {
        this.journStreakCount = journStreakCount;
    }

    public String getLastMedActivityDate() {
        return lastMedActivityDate;
    }

    public void setLastMedActivityDate(String lastMedActivityDate) {
        this.lastMedActivityDate = lastMedActivityDate;
    }

    public String getLastAffActivityDate() {
        return lastAffActivityDate;
    }

    public void setLastAffActivityDate(String lastAffActivityDate) {
        this.lastAffActivityDate = lastAffActivityDate;
    }

    public String getLastYogaActivityDate() {
        return lastYogaActivityDate;
    }

    public void setLastYogaActivityDate(String lastYogaActivityDate) {
        this.lastYogaActivityDate = lastYogaActivityDate;
    }

    public String getLastJournActivityDate() {
        return lastJournActivityDate;
    }

    public void setLastJournActivityDate(String lastJournActivityDate) {
        this.lastJournActivityDate = lastJournActivityDate;
    }

    public String calculateAge() {
        String month = birthday.substring(0, 2);
        String day = birthday.substring(3, 5);
        String year = birthday.substring(6);
        LocalDate bday = LocalDate.parse(year + "-" + month + "-" + day);
        LocalDate today = LocalDate.now();
        return String.valueOf(bday.until(today).getYears());
    }

    public String birthMonth(){
        return birthday.substring(0, 2);
    }


    public String birthYear(){
        return birthday.substring(6);
    }

    public String birthDay(){
        return birthday.substring(3, 5);
    }


    @Override
    public String toString() {
        String result = "name: " + firstName + " " + lastName +
                "\nborn on : " + birthday +
                "\nand lives in " + city + " , " + country +
                "\nemail: " + email +
                "\npronouns: " + pronouns +
                "\npassword: " + password +
                "\njoined: " + joined +
                "\nusername: " + userName +
                "\nid: " + id +
                "\nBio: " + bio;

        return result;
    }




}
