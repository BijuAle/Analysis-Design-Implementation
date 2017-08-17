/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import subject_observer.EventObserver;

/**
 *
 * @author Biju Ale
 */
public class User implements EventObserver {

    private int id;
    private String firstName, lastName, email;
    private String location;
    private String password;
    ArrayList<Event> createdEvents;
    private ArrayList<TimeSlot> availableTimeSlots;
    private ArrayList<TimeSlot> unavailableTimeSlots;
    HashMap<String, String> notifications;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<TimeSlot> getAvailableTimeSlots() {
        return availableTimeSlots;
    }

    public void setAvailableTimeSlots(ArrayList<TimeSlot> availableTimeSlots) {
        this.availableTimeSlots = availableTimeSlots;
    }

    public ArrayList<TimeSlot> getUnavailableTimeSlots() {
        return unavailableTimeSlots;
    }

    public void setUnavailableTimeSlots(ArrayList<TimeSlot> unavailableTimeSlots) {
        this.unavailableTimeSlots = unavailableTimeSlots;
    }

    public void displayNotification(){
        for (int i = 0; i < notifications.size(); i++) {
            
        }
    }
    @Override
    public void showNotification(Event event) {
        notifications.put("Event Name", email);
    }

    @Override
    public void showCandidateTimeSlots(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
