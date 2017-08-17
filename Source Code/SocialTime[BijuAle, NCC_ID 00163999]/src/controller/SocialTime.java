/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Biju Ale
 */
public class SocialTime {

    private static UserController userController;
    private static TimeSlotController timeSlotController;
    private static EventController eventController;

    //Getters using singleton pattern to instantiate and return controller objects
    public static UserController getUserController() {
        if (userController == null) {
            userController = new UserController();
        }
        return userController;
    }

    public static TimeSlotController getTimeSlotController() {
        if (timeSlotController == null) {
            timeSlotController = new TimeSlotController();
        }
        return timeSlotController;
    }

    public static EventController getEventController() {
        if (eventController == null) {
            eventController = new EventController();
        }
        return eventController;
    }

}
