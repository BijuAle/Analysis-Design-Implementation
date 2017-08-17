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
public class Invitee extends User {

    public boolean viewEventList() {
        return false;
    }

    public boolean acceptInvitation() {
        return false;

    }

    public boolean rejectInvitation() {
        return false;

    }

    public boolean voteTimeSlot() {
        return false;

    }
}
