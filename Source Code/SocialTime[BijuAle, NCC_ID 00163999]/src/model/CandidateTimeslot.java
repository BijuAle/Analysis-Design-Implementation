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
public class CandidateTimeslot {

    private TimeSlot timeSlot;
    private int voteCount;

    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    public void setTs(TimeSlot ts) {
        this.timeSlot = ts;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean setVoteCount(int voteCount) {
        this.voteCount = voteCount;
        return true;
    }
}
