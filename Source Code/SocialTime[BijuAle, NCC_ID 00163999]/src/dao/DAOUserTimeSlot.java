/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dbutil.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.TimeSlot;
import model.User;

/**
 *
 * @author Biju Ale
 */
public class DAOUserTimeSlot implements DAO {

    Connection conn;

    public DAOUserTimeSlot() {
        conn = ConnectionFactory.getConnection();
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
        User user = (User) obj;
        String toUserTimeSlotTable = "INSERT INTO tbl_user_timeslot(user_id, day_id, timeslot_type_id, timeslot_hr_block) VALUES(?,?,?,?)";
        //Stmt with returnable auto-generated key
        try {
            PreparedStatement stmt = conn.prepareStatement(toUserTimeSlotTable, Statement.RETURN_GENERATED_KEYS);
            //Persisting all available timeslots
            for (TimeSlot availableTimeSlot : user.getAvailableTimeSlots()) {
                stmt.setInt(1, user.getId());
                stmt.setInt(2, availableTimeSlot.getDay());
                stmt.setInt(3, 1);
                stmt.setInt(4, availableTimeSlot.getHour());
                stmt.executeUpdate();
            }
            //Persisting all unavailable timeslots
            for (TimeSlot unavailableTimeSlot : user.getUnavailableTimeSlots()) {
                stmt.setInt(1, user.getId());
                stmt.setInt(2, unavailableTimeSlot.getDay());
                stmt.setInt(3, 2);
                stmt.setInt(4, unavailableTimeSlot.getHour());
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

    @Override
    public ArrayList<Object> getAll(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
