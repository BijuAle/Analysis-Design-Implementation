/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;
import subject_observer.Observer;
import subject_observer.Subject;

/**
 *
 * @author Biju Ale
 */
public class Event implements Subject {

    private int id;
    private User creator;
    private String name;
    private String location, description;
    private int duration;
    private Date date;
    private int minNoOfAttendees;
    private int minThresholdPercent;
    private ArrayList<User> invitee;
    private ArrayList<User> attendees;

    

    @Override
    public boolean registerObserver(Observer O) {
        invitee.add((User) O);
        return true;
    }

    @Override
    public boolean removeObserver(Observer O) {
        invitee.remove((User) O);
        return true;

    }

    @Override
    public boolean notifyObserver(Observer O) {
        for (User eachObserver : invitee) {
            eachObserver.notify();
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMinNoOfAttendees() {
        return minNoOfAttendees;
    }

    public void setMinNoOfAttendees(int minNoOfAttendees) {
        this.minNoOfAttendees = minNoOfAttendees;
    }

    public int getMinThresholdPercent() {
        return minThresholdPercent;
    }

    public void setMinThresholdPercent(int minThresholdPercent) {
        this.minThresholdPercent = minThresholdPercent;
    }

    public ArrayList<User> getInvitee() {
        return invitee;
    }

    public void setInvitee(ArrayList<User> invitee) {
        this.invitee = invitee;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }
}
