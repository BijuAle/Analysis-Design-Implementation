/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subject_observer;

import model.Event;

/**
 *
 * @author Biju Ale
 */
public interface EventObserver {

    void showNotification(Event event);
    void showCandidateTimeSlots(Event event);

}
