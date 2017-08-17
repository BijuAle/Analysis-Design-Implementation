/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import model.TimeSlot;
import model.User;
import model.factory.TimeSlotFactory;

/**
 *
 * @author Biju Ale
 */
public class TimeSlotController {

    //Returns list of timeslot objects parsed from string
    public ArrayList<TimeSlot> parseTS(ArrayList<String> allStringTimeSlots)    {
        TimeSlot timeSlot;
        ArrayList<TimeSlot> allParsedTimeSlots = new ArrayList();
        
        for (String eachStringTimeSlot : allStringTimeSlots) {
            String tokens[] = eachStringTimeSlot.split(";");
            int day = Integer.parseInt(tokens[0]);
            int hour = Integer.parseInt(tokens[1]);
            timeSlot = TimeSlotFactory.getTimeSlot(day, hour);
            allParsedTimeSlots.add(timeSlot);
        }
        return allParsedTimeSlots;
    }

    public ArrayList<TimeSlot> getUncertainTimeSlots(User invitee) {
        ArrayList<TimeSlot> uncertainTimeSlots = new ArrayList();
        TimeSlot uncertainTimeslot;

        ArrayList<String> allKeys = generateAllKeyCombinations();
        ArrayList<String> mergedKeys = getMergerdKeysOfUserTimeSlot(invitee);//Available+Unavailble
        ArrayList<String> uncertainTimeSlotKeys = new ArrayList();
        allKeys.removeAll(mergedKeys);

        uncertainTimeSlotKeys.addAll(allKeys);//allKeys now contain only uncertain timeslot keys.

        HashMap<String, Object> timeSlotParameters;

        //Get uncertain timeslot objects from factory based on all uncertain timeslot keys
        for (String uncertainTimeSlotKey : uncertainTimeSlotKeys) {
            String tokens[] = uncertainTimeSlotKey.split(";");
            int day = Integer.parseInt(tokens[0]);
            int hour = Integer.parseInt(tokens[1]);
            uncertainTimeslot = TimeSlotFactory.getTimeSlot(day, hour);
            uncertainTimeSlots.add(uncertainTimeslot);
        }
        return uncertainTimeSlots;
    }

    //Generate all possible day-hour combination for timeslots (available+unavailable+uncertain)
    public ArrayList<String> generateAllKeyCombinations() {
        ArrayList<String> allKeys = new ArrayList();
        for (int dayNo = 1; dayNo < 8; dayNo++) {
            for (int hour = 0; hour < 24; hour++) {
                String key = Integer.toString(dayNo) + ";" + Integer.toString(hour);
                allKeys.add(key);
            }
        }
        return allKeys;
    }

//    Get merged keys (available+unavailable timeslot) of user. 
    public ArrayList<String> getMergerdKeysOfUserTimeSlot(User invitee) {
        ArrayList<String> mergedKeys = new ArrayList();

        ArrayList<TimeSlot> mergedList = new ArrayList();
        mergedList.addAll(invitee.getAvailableTimeSlots());
        mergedList.addAll(invitee.getUnavailableTimeSlots());

        for (TimeSlot timeSlot : mergedList) {
            String key = Integer.toString(timeSlot.getDay()) + ";" + Integer.toString(timeSlot.getHour());
            mergedKeys.add(key);
        }
        return mergedKeys;
    }

    public ArrayList<TimeSlot> getFeasibleTimeSlots(ArrayList<User> invitees, int minThreshHold) {
        ArrayList<TimeSlot> feasibleTimeSlots = new ArrayList();
        ArrayList<TimeSlot> mergedListOfTmeSlots = new ArrayList();//Available & Unncertain timeslots of all invitee merged in 1 list

        ArrayList<TimeSlot> availableTimeSlot, unCertainTimeSlot;

        Set<TimeSlot> uniqueObjects;

        //Add all timeslots of each invitee into a single list
        for (User invitee : invitees) {
            mergedListOfTmeSlots.addAll(invitee.getAvailableTimeSlots());
            mergedListOfTmeSlots.addAll(getUncertainTimeSlots(invitee));
        }

        //Find the unique set from combined arraylist
        uniqueObjects = new HashSet<TimeSlot>(mergedListOfTmeSlots);

        //Check feasibility of timeslots with min threshold.
        for (TimeSlot uniqueTimeSlot : uniqueObjects) {
            int freq = Collections.frequency(mergedListOfTmeSlots, uniqueTimeSlot);
            if (freq >= minThreshHold) {
                feasibleTimeSlots.add(uniqueTimeSlot);
            }
        }
        return feasibleTimeSlots;
    }

}
