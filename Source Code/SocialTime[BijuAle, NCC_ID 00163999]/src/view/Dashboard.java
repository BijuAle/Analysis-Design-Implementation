/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EventController;
import controller.UserController;
import dao.SimpleDAOFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Event;
import subject_observer.Observer;

/**
 *
 * @author Biju Ale
 */
public class Dashboard extends JFrame implements ActionListener, Observer {

    static Dashboard dashboard;

    JTable tblCreatedEvents, tblInvitedEvents;
    TableModel tableModel;
    JScrollPane scrollPane;
    JPanel pnlTable, pnlTable2;
    private final JButton btnLogout;
    private final JButton btnEditProfile;
    private final JButton btnCreateEvent;
    private final JPanel pnlWest;
    private final JButton btnEditEvent;

    EventController eventController = new EventController();
    private final JButton btnViewInvitations;

    Color foreGroundColor = new Color(102, 255, 204);
    Color backgroundColor = new Color(102, 0, 51);

    public static Dashboard getInstanceOf() {
        if (dashboard == null) {
            dashboard = new Dashboard();
            dashboard.setVisible(true);
        }
        dashboard.setVisible(true);
        return dashboard;
    }

    private Dashboard() {
        Font f = new javax.swing.plaf.FontUIResource("Georgia", Font.PLAIN, 12);
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }

        setTitle("Dashboard - Social Time");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);

        pnlWest = new JPanel(new FlowLayout());
        pnlWest.setOpaque(false);
        pnlWest.setPreferredSize(new Dimension(150, 20));

        JLabel lblFullName = new JLabel(UserController.getLoggedInUser().getFirstName() + " " + UserController.getLoggedInUser().getLastName());
        lblFullName.setFont(new Font("Georgia", Font.PLAIN, 11));
        lblFullName.setForeground(foreGroundColor);
        pnlWest.add(lblFullName, BorderLayout.WEST);

        JLabel lblDashboard = new JLabel("DASHBOARD");
        lblDashboard.setFont(new Font("Georgia", Font.PLAIN, 20));
        lblDashboard.setForeground(foreGroundColor);
        pnlWest.add(lblDashboard);

        btnLogout = new JButton("Logout");
        btnLogout.addActionListener(this);
        pnlWest.add(btnLogout, BorderLayout.WEST);
//        pnlWest.add(btnLogout);

        btnEditProfile = new JButton("Edit Profile");
        btnEditProfile.addActionListener(this);
        pnlWest.add(btnEditProfile);

        btnCreateEvent = new JButton("Create Event");
        btnCreateEvent.addActionListener(this);
        pnlWest.add(btnCreateEvent);

        btnEditEvent = new JButton("Edit Event");
        btnEditEvent.addActionListener(this);
        pnlWest.add(btnEditEvent);

        btnViewInvitations = new JButton("View Invitations");
        btnViewInvitations.addActionListener(this);
        pnlWest.add(btnViewInvitations);

        pnlTable = new JPanel();
        pnlTable2 = new JPanel();
        //Blank tblCreatedEvents instantiation
        String[] columnHeadings = {"EVENT ID", "NAME", "DESCRIPTION", "DURATION", "LOCATION", "MIN. ATTENDEES", "MIN THRESHOLD"};
        tableModel = new DefaultTableModel(columnHeadings, 0);
        tblCreatedEvents = new JTable(tableModel) {//Creating tblCreatedEvents object & making tblCreatedEvents anonymous class to make cell editable false.
            @Override
            public boolean isCellEditable(int data, int column) {
                return false;
            }
        };

        //Inserting vertical scrollbar to tblCreatedEvents
        tblCreatedEvents.setAutoCreateRowSorter(true);
        tblCreatedEvents.setPreferredScrollableViewportSize(new Dimension(890, 400));
        tblCreatedEvents.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(tblCreatedEvents);
        pnlTable.add(scrollPane);

        add(pnlWest, BorderLayout.WEST);
        add(pnlTable, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void updateTable(ArrayList<Object> events) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblCreatedEvents.getModel();
        model.setRowCount(0);
        String[] tuple = new String[7];
        for (Object event : events) { //Loop through all indices/rooms in this stack
            Event eachEvent = (Event) event;
            tuple[0] = Integer.toString(eachEvent.getId()); //Using getters of each transaction to access their state/field.
            tuple[1] = eachEvent.getName();
            tuple[2] = eachEvent.getDescription();
            tuple[3] = Integer.toString(eachEvent.getDuration());
            tuple[4] = eachEvent.getLocation();
            tuple[5] = Integer.toString(eachEvent.getMinNoOfAttendees());
            tuple[6] = Integer.toString(eachEvent.getMinThresholdPercent());
            model.addRow(tuple);
            Arrays.fill(tuple, null); //Clearing all elements from tuple for next transaction data
        }
//        tblCreatedEvents.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int selectedRowNo = tblCreatedEvents.getSelectedRow();

        Object btnSource = (Object) e.getSource();
        if (btnSource.equals(btnCreateEvent)) {
            GUIFactory.getInstanceOf("addEventFrame");
        } else if (btnSource.equals(btnEditEvent)) {
            if (selectedRowNo != -1) {//If row is selected (selected row no. is greater than -1)
                UpdateEventFrame updateEventFrame = (UpdateEventFrame) GUIFactory.getInstanceOf("updateEventFrame");
                updateEventFrame.setVisible(true);

                int eventID = Integer.parseInt((String) tblCreatedEvents.getValueAt(selectedRowNo, 0));
                updateEventFrame.setEventID(eventID);

                String eventName = (String) tblCreatedEvents.getValueAt(selectedRowNo, 1);
                updateEventFrame.setTxtEventName(eventName);

                String description = (String) tblCreatedEvents.getValueAt(selectedRowNo, 2);
                updateEventFrame.setTxtDescription(description);

                int duration = Integer.parseInt((String) tblCreatedEvents.getValueAt(selectedRowNo, 3));
                updateEventFrame.setSpnDuration(duration);

                String location = (String) tblCreatedEvents.getValueAt(selectedRowNo, 4);
                updateEventFrame.setTxtLocation(location);

                int minAttendee = Integer.parseInt((String) tblCreatedEvents.getValueAt(selectedRowNo, 5));
                updateEventFrame.setSpnMinAttendee(minAttendee);

                int minThreshold = Integer.parseInt((String) tblCreatedEvents.getValueAt(selectedRowNo, 6));
                updateEventFrame.setSpnMinThreshold(minThreshold);

            } else {
                JOptionPane.showMessageDialog(null, "No cell selected", "Select event from table to update!", JOptionPane.WARNING_MESSAGE);
            }
        } else if (btnSource.equals(btnEditProfile)) {
            GUIFactory.getInstanceOf("updateProfileFrame");
        } else if (btnSource.equals(btnLogout)) {
            this.dispose();
            GUIFactory.getInstanceOf("loginFrame");

        } else if (btnSource.equals(btnViewInvitations)) {
            SimpleDAOFactory.getDAO("DAOInviteeEvent").getAll();
            GUIFactory.getInstanceOf("invitationsFrame");

        }
    }

}
