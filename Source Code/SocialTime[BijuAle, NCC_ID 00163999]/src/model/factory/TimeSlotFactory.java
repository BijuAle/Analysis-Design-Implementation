/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import java.util.HashMap;
import java.util.Map;
import model.TimeSlot;

/**
 *
 * @author Biju Ale
 */
public class TimeSlotFactory {

    public static Map<String, TimeSlot> allPossibleTimeslots = new HashMap<>();
    TimeSlot timeslot;

    public static TimeSlot getTimeSlot(int day, int hour) {
        String key = Integer.toString(day) + Integer.toString(hour);
        if (allPossibleTimeslots.containsKey(key)) {
            return allPossibleTimeslots.get(key);
        }

        TimeSlot timeslot = new TimeSlot(day, hour);
        allPossibleTimeslots.put(key, timeslot);
        return timeslot;
    }

}
