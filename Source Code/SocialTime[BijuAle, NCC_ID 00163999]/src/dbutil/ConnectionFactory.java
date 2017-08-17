/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Biju Ale
 */
public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/db_socialtime";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection conn;

    private ConnectionFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getStackTrace(), ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
