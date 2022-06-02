/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Alessandra
 */
public class DBConnection {

    private static final String USERNAME = "minesweeper";
    private static final String PASSWORD = "minesweeper";
    private static final String URL = "jdbc:derby:/MinesweeperDB; create=true";

    Connection connection;

    public DBConnection() {
        makeConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    private void makeConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println(URL + " has been connected!");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
