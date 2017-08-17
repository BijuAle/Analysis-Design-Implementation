/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import java.util.ArrayList;
import model.Event;
import model.User;

/**
 *
 * @author Biju Ale
 */
public class EventFactory {

    //for adding new event
    public static Event getModel(User creator, String eventName, String location, String description, int duration, int minAttendees, int minAThreshold, ArrayList<User> invitees) {
        Event event = new Event();
        event.setCreator(creator);
        event.setName(eventName);
        event.setLocation(location);
        event.setDescription(description);
        event.setDuration(duration);

        event.setMinNoOfAttendees(minAttendees);
        event.setMinThresholdPercent(minAThreshold);
        event.setInvitee(invitees);
        return event;
    }

    //for updating exsiting event
    public static Event getModel(int eventID, User creator, String eventName, String location, String description, int duration, int minAttendees, int minAThreshold) {
        Event event = new Event();
        event.setId(eventID);
        event.setCreator(creator);
        event.setName(eventName);
        event.setLocation(location);
        event.setDescription(description);
        event.setDuration(duration);
        event.setMinNoOfAttendees(minAttendees);
        event.setMinThresholdPercent(minAThreshold);
        return event;
    }
}
