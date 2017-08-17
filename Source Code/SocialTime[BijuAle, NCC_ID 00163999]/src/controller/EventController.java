/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.SimpleDAOFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import model.Event;
import model.User;
import model.factory.EventFactory;

/**
 *
 * @author Biju Ale
 */
public class EventController {

    private DAO daoEvent, daoInviteeEvent;
    private Event event;

    public boolean saveEvent(String eventName, String location, String description, int duration, int minAttendees, int minAThreshold, Set invitee) {

        //Parse set to arraylist and remove null
        ArrayList<User> invitees = new ArrayList();
        invitees.addAll(invitee);
        invitees.removeAll(Collections.singleton(null));
        
        //Persist invitee to DB
        for (User invitee1 : invitees) {
            System.out.println(invitee1.getId());
        }
        
        //Get event object
        event = EventFactory.getModel(UserController.getLoggedInUser(), eventName, location, description, duration, minAttendees, minAThreshold, invitees);
        //Get DAOEvent
        daoEvent = SimpleDAOFactory.getDAO("DAOEvent");

        //Persist event to DB using DAOEvent
        daoEvent.insert(event);

        event.setInvitee(invitees);

        daoInviteeEvent = SimpleDAOFactory.getDAO("DAOInviteeEvent");
        daoInviteeEvent.insert(event);
        refreshTable(event.getCreator().getId());
        return true;
    }

    public void refreshTable(int whoseTable) {
        daoEvent = SimpleDAOFactory.getDAO("DAOEvent");
        daoEvent.getAll(whoseTable);
    }

    public boolean updateEvent(String eventName, int eventID, String location, String description, int duration, int minAttendees, int minAThreshold) {
        //Get event object
        event = EventFactory.getModel(eventID, UserController.getLoggedInUser(), eventName, location, description, duration, minAttendees, minAThreshold);
        //Get DAOEvent
        daoEvent = SimpleDAOFactory.getDAO("DAOEvent");
        //Persist event to DB using DAOEvent
        daoEvent.update(event);
        //Persist event in DB
        refreshTable(event.getCreator().getId());
        return true;
    }

}
