/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subject_observer;

/**
 *
 * @author Biju Ale
 */
public interface Subject {

    boolean registerObserver(Observer O);
    boolean removeObserver(Observer O);
    boolean notifyObserver(Observer O);
}
