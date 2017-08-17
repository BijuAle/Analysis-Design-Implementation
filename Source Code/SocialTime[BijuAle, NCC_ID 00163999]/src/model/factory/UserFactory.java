/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import java.util.ArrayList;
import model.TimeSlot;
import model.User;

/**
 *
 * @author Biju Ale
 */
public class UserFactory {

    public static User getModel(String firstName, String lastName, String email, String location, String password, ArrayList<TimeSlot> allAvailableTS, ArrayList<TimeSlot> allUnavailableTS) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLocation(location);
        user.setPassword(password);
        user.setAvailableTimeSlots(allAvailableTS);
        user.setUnavailableTimeSlots(allUnavailableTS);
        return user;
    }

}
