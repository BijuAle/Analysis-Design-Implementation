/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Biju Ale
 */
public abstract class GUIFactory {

    private static LoginFrame loginFrame;
    private static RegistrationFrame regisrationFrame;
    private static AddEventFrame addEventFrame;
    private static Dashboard dashboard;
    private static UpdateEventFrame updateEventFrame;
    private static UpdateProfileFrame updateProfileFrame;
    private static InvitationsFrame invitationsFrame;

    //Singleton instantiation of GUI elements
    public static Object getInstanceOf(String GUIType) {
        switch (GUIType) {
            case "loginFrame":
                if (loginFrame == null) {
                    return new LoginFrame();
                } else {
                    return loginFrame;
                }
            case "regisrationFrame":
                if (regisrationFrame == null) {
                    return new RegistrationFrame();
                } else {
                    return regisrationFrame;
                }
            case "dashboard":
                if (dashboard == null) {
                    dashboard = Dashboard.getInstanceOf();
                } else {
                    return dashboard;
                }
            case "addEventFrame":
                if (addEventFrame == null) {
                    return new AddEventFrame();
                } else {
                    return addEventFrame;
                }
            case "updateEventFrame":
                if (updateEventFrame == null) {
                    return new UpdateEventFrame();
                } else {
                    return updateEventFrame;
                }
            case "updateProfileFrame":
                if (updateProfileFrame == null) {
                    return new UpdateProfileFrame();
                } else {
                    return updateProfileFrame;
                }
            case "invitationsFrame":
                if (invitationsFrame == null) {
                    invitationsFrame = InvitationsFrame.getInstanceOf();
                } else {
                    return invitationsFrame;
                }
        }
        return null;
    }
}
