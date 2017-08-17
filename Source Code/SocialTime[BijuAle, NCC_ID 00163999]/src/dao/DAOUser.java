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
import model.User;

/**
 *
 * @author Biju Ale
 */
public class DAOUser implements DAO {

    Connection conn;

    public DAOUser() {
        conn = ConnectionFactory.getConnection();
    }

    @Override
    public ArrayList<Object> getAll() {
        String sql = "SELECT * FROM tbl_user";
        ArrayList<Object> users = new ArrayList();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setLocation(rs.getString("location"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return users;

    }

    @Override
    public Object get(int id) {
        String sql = "SELECT * FROM tbl_user WHERE id = ?";
        User user = new User();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(id, id);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setLocation(rs.getString("location"));
                user.setPassword(rs.getString("password"));
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }

    //Overload for user authentication
    public Object get(String email, String password) {
        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        User user = new User();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setEmail(rs.getString("email"));
                user.setLocation(rs.getString("location"));
                user.setPassword(rs.getString("password"));
            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return user;
    }

    @Override
    public boolean insert(Object obj) {
        User user = (User) obj;
        try {
            String toUserTable = "INSERT INTO tbl_user(firstName, lastName, email, location, password) VALUES(?,?,?,?,?)";

            //Stmt with returnable auto-generated key
            PreparedStatement stmt = conn.prepareStatement(toUserTable, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getLocation());
            stmt.setString(5, user.getPassword());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int userID = rs.getInt(1);//Return auto-generataed key to set ID of object
            user.setId(userID);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean update(Object obj) {
        String sql = "UPDATE tbl_user SET firstName = ?, lastName = ?, email = ?, location = ?, password = ? WHERE id = ?";
        User user = (User) obj;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getLocation());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getId());

            int rowsUpdated = stmt.executeUpdate();
            stmt.close();

            if (rowsUpdated > 0) {
                return true;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean delete(Object obj) {
        String sql = "DELETE FROM tbl_user WHERE id = ?";
        User user = (User) obj;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, user.getId());

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
