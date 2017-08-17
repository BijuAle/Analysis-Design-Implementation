/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Biju Ale
 */
public class TimeSlot {

    final private int day;
    final private int hour;

    public TimeSlot(int day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    @Override
    public String toString() {
        return Integer.toString(day) + " " + Integer.toString(hour);
    }
}
