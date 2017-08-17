/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Biju Ale
 */
public class SimpleDAOFactory {

    static DAO daoUser, daoEvent, daoUserTimeSlot, daoInviteeEvent;

    public static DAO getDAO(String DAOType) {

        //Singleton pattern applied to DAO instantiations
        switch (DAOType) {
            case "DAOUser":
                if (daoUser == null) {
                    return new DAOUser();
                } else {
                    return daoUser;
                }
            case "DAOEvent":
                if (daoEvent == null) {
                    return new DAOEvent();
                } else {
                    return daoEvent;
                }
            case "DAOUserTimeSlot":
                if (daoUserTimeSlot == null) {
                    return new DAOUserTimeSlot();
                } else {
                    return daoUserTimeSlot;
                }
            case "DAOInviteeEvent":
                if (daoInviteeEvent == null) {
                    return new DAOInviteeEvent();
                } else {
                    return daoInviteeEvent;
                }
        }
        return null;
    }

}
