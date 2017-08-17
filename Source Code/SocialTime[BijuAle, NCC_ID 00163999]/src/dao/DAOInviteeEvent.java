/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dbutil.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Event;
import model.User;
import view.InvitationsFrame;

/**
 *
 * @author Biju Ale
 */
public class DAOInviteeEvent implements DAO {

    static InvitationsFrame inf;
    private ArrayList events = new ArrayList();

    Connection conn;

    public DAOInviteeEvent() {
        this.conn = ConnectionFactory.getConnection();
    }

    @Override
    public ArrayList<Object> getAll(int id) {
        String sql = "SELECT e. FROM tbl_event WHERE creator = ?";
        events.clear(); //Clear history
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getInt("id"));
                event.setName(rs.getString("name"));
                event.setLocation(rs.getString("location"));
                event.setDescription(rs.getString("description"));
                event.setDuration(rs.getInt("duration"));
                event.setDate(rs.getDate("date"));
                event.setMinNoOfAttendees(rs.getInt("minNoOfAttendees"));
                event.setMinThresholdPercent(rs.getInt("minThresholdPercent"));
                events.add(event);
            }
            // Build table model in dashboard  
            inf.updateTable(events);
//            notifyObserver(dashboard);
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return events;
    }

    @Override
    public ArrayList<Object> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(Object obj) {
        Event event = (Event) obj;
        ArrayList<User> invitees = event.getInvitee();
        String toEventInviteeTable = "INSERT INTO tbl_event_invitee(event_id, user_id) VALUES(?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(toEventInviteeTable);
            //Persisting all invitee with events
            for (User eachInvitee : invitees) {
                stmt.setInt(1, event.getId());
                stmt.setInt(2, eachInvitee.getId());
                stmt.executeUpdate();
            }
            stmt.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean update(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
