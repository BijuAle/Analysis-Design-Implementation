/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EventController;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Event;
import subject_observer.Observer;

/**
 *
 * @author Biju Ale
 */
public class InvitationsFrame extends JFrame implements ActionListener, Observer {

    static InvitationsFrame invitationsFrame;

    JTable tblInvitations, tblInvitedEvents;
    TableModel tableModel;
    JScrollPane scrollPane;
    JPanel pnlTable, pnlTable2;
    private final JPanel pnlWest;

    EventController eventController = new EventController();

    Color foreGroundColor = new Color(102, 255, 204);
    Color backgroundColor = new Color(102, 0, 51);

    public static InvitationsFrame getInstanceOf() {
        if (invitationsFrame == null) {
            invitationsFrame = new InvitationsFrame();
            invitationsFrame.setVisible(true);
        }
        invitationsFrame.setVisible(true);
        return invitationsFrame;
    }
    private final JButton btnAccept, btnDecline;
    private final JButton btnDashboard;

    private InvitationsFrame() {
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
//        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);

        pnlWest = new JPanel(new FlowLayout());
        pnlWest.setOpaque(false);
        pnlWest.setPreferredSize(new Dimension(150, 20));

        JLabel lblTitle = new JLabel("Invitations");
        lblTitle.setFont(new Font("Georgia", Font.PLAIN, 20));
        lblTitle.setForeground(foreGroundColor);
        pnlWest.add(lblTitle);

        btnDashboard = new JButton("Dashboard");
        btnDashboard.addActionListener(this);
        pnlWest.add(btnDashboard);

        btnAccept = new JButton("Accept");
        btnAccept.addActionListener(this);
        pnlWest.add(btnAccept);

        btnDecline = new JButton("Decline");
        btnDecline.addActionListener(this);
        pnlWest.add(btnDecline);

        pnlTable = new JPanel();
        pnlTable2 = new JPanel();
        //Blank tblCreatedEvents instantiation
        String[] columnHeadings = {"EVENT ID", "NAME", "DESCRIPTION", "DURATION", "LOCATION", "MIN. ATTENDEES", "MIN THRESHOLD"};
        tableModel = new DefaultTableModel(columnHeadings, 0);
        tblInvitations = new JTable(tableModel) {//Creating tblCreatedEvents object & making tblCreatedEvents anonymous class to make cell editable false.
            @Override
            public boolean isCellEditable(int data, int column) {
                return false;
            }
        };

        //Inserting vertical scrollbar to tblCreatedEvents
        tblInvitations.setAutoCreateRowSorter(true);
        tblInvitations.setPreferredScrollableViewportSize(new Dimension(890, 400));
        tblInvitations.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(tblInvitations);
        pnlTable.add(scrollPane);

        add(pnlWest, BorderLayout.WEST);
        add(pnlTable, BorderLayout.CENTER);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void updateTable(ArrayList<Object> events) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) tblInvitations.getModel();
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
        int selectedRowNo = tblInvitations.getSelectedRow();
        Object btnSource = (Object) e.getSource();
        if (btnSource.equals(btnDashboard)) {
            this.dispose();
            GUIFactory.getInstanceOf("dashboard");

        }

    }

}
