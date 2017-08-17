/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.DAO;
import dao.DAOUser;
import java.util.ArrayList;
import model.TimeSlot;
import dao.SimpleDAOFactory;
import model.User;
import model.factory.UserFactory;

/**
 *
 * @author Biju Ale
 */
public class UserController {

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    private User user;
    private DAO daoUser, daoUserTimeSlot;
    private static User loggedInUser;

    public boolean registerUser(String firstName, String lastName, String email, String location, String password, ArrayList<TimeSlot> allAvailableTS, ArrayList<TimeSlot> allUnavailableTS) {
        //Get user object
        user = UserFactory.getModel(firstName, lastName, email, location, password, allAvailableTS, allUnavailableTS);
        //Get DAOUser
        daoUser = SimpleDAOFactory.getDAO("DAOUser");
        //Persist user to DB using DAOUser
        daoUser.insert(user);
        //Get DAOUserTimeSlot 
        daoUserTimeSlot = SimpleDAOFactory.getDAO("DAOUserTimeSlot");
        //Persist user and timeslot details to DB using DAOUserTimeSlot
        daoUserTimeSlot.insert(user);
        return true;
    }

    public boolean authenticateUser(String email, String password) {
        loggedInUser = (User) ((DAOUser) SimpleDAOFactory.getDAO("DAOUser")).get(email, password);
        if (loggedInUser.getId() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void updateProfile(User loggedInUser) {
        //Get DAOUser
        daoUser = SimpleDAOFactory.getDAO("DAOUser");
        //update user to DB using DAOUser
        daoUser.update(loggedInUser);
    }
}
