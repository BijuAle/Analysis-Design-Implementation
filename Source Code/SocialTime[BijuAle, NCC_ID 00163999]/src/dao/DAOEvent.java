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
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Event;
import model.User;
import subject_observer.Observer;
import subject_observer.Subject;
import view.Dashboard;

/**
 *
 * @author Biju Ale
 */
public class DAOEvent implements DAO, Subject {

    Connection conn;
    //private Dashboard dashboard = (Dashboard) GUIFactory.getInstanceOf("dashboard");
    public static Dashboard dashboard;
    private ArrayList events = new ArrayList();

    public DAOEvent() {
        conn = ConnectionFactory.getConnection();
    }

    @Override
    public ArrayList<Object> getAll() {
        String sql = "SELECT * FROM tbl_event";

        events.clear(); //Clear history
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
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
//            dashboard.updateTable(events);
//            notifyObserver(dashboard);
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return events;
    }

    @Override
    public Object get(int id) {
        String sql = "SELECT * FROM tbl_event WHERE id = ?";
        Event event = new Event();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(id, id);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                event.setName(rs.getString("name"));
                event.setLocation(rs.getString("location"));
                event.setDescription(rs.getString("description"));
                event.setDuration(rs.getInt("duration"));
                event.setDate(rs.getDate("date"));
                event.setMinNoOfAttendees(rs.getInt("minNoOfAttendees"));
                event.setMinThresholdPercent(rs.getInt("minThrestholdPercent"));
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return event;
    }

    @Override
    public boolean insert(Object obj) {
        String toEventTable = "INSERT INTO tbl_event(creator, name, location, description, duration, minNoOfAttendees, minThresholdPercent) VALUES(?, ?,?,?,?,?,?)";
        Event event = (Event) obj;
        int creatorID = ((User) event.getCreator()).getId();
        try {
            PreparedStatement stmt = conn.prepareStatement(toEventTable, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, (creatorID));
            stmt.setString(2, event.getName());
            stmt.setString(3, event.getLocation());
            stmt.setString(4, event.getDescription());
            stmt.setDouble(5, event.getDuration());
            stmt.setInt(6, event.getMinNoOfAttendees());
            stmt.setInt(7, event.getMinThresholdPercent());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int eventID = rs.getInt(1);//Return auto-generataed key to set ID of object
            event.setId(eventID);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean update(Object obj) {
        String sql = "UPDATE tbl_event SET name = ?, location = ?, description = ?, duration = ?, minNoOfAttendees = ?, minThresholdPercent = ? WHERE id = ?";
        Event event = (Event) obj;
        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getLocation());
            stmt.setString(3, event.getDescription());
            stmt.setInt(4, event.getDuration());
            stmt.setInt(5, event.getMinNoOfAttendees());
            stmt.setInt(6, event.getMinThresholdPercent());
            stmt.setInt(7, event.getId());
            int rowsUpdated = stmt.executeUpdate();
            stmt.close();

            if (rowsUpdated > 0) {
//                notifyObserver(dashboard);
                return true;

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    @Override
    public boolean delete(Object obj) {
        String sql = "DELETE FROM tbl_event WHERE id = ?";
        Event event = (Event) obj;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, event.getId());

            int rowsDeleted = stmt.executeUpdate();
            stmt.close();

            if (rowsDeleted > 0) {
                return true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public ArrayList<Object> getAll(int id) {
        String sql = "SELECT * FROM tbl_event WHERE creator = ?";
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
            dashboard.updateTable(events);
//            notifyObserver(dashboard);
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return events;
    }

    @Override
    public boolean registerObserver(Observer O) {
        this.dashboard = (Dashboard) O;
        return true;
    }

    @Override
    public boolean removeObserver(Observer O) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean notifyObserver(Observer O) {
        try {
            dashboard.updateTable(events);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

}
