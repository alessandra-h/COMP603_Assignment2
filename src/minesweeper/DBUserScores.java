/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Alessandra
 */
public class DBUserScores extends DBUserTable {
    // For updating and getting the user's scores
    
    /**
     * Update the current user's wins by incrementing 1
     *
     * @param user
     */
    public void updateUserWins(User user) {
        try {
            setStatement(getConnection().createStatement());
            String sql = "UPDATE USERS SET WINS = "+(user.getWins()+1)+" WHERE USERNAME = '"+user.getUsername()+"'";
            getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Update the current user's losses by incrementing 1
     *
     * @param user
     */
    public void updateUserLosses(User user) {
        try {
            setStatement(getConnection().createStatement());
            String sql = "UPDATE USERS SET LOSSES = "+(user.getLosses()+1)+" WHERE USERNAME = '"+user.getUsername()+"'";
            getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns the number of the user's wins from the database.
     * @param user
     * @return 
     */
    public int getUserWins(User user) {
        int wins = 0;
        try {
            setStatement(this.getConnection().createStatement());
            String sql = "SELECT WINS FROM USERS WHERE USERNAME = '" + user.getUsername() + "'";
            ResultSet rs = getStatement().executeQuery(sql);
            while (rs.next()) {
                wins = rs.getInt("WINS");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return wins;
    }

    /**
     * Returns the number of the user's losses from the database.
     * @param user
     * @return 
     */
    public int getUserLosses(User user) {
        int losses = 0;
        try {
            setStatement(this.getConnection().createStatement());
            String sql = "SELECT LOSSES FROM USERS WHERE USERNAME = '" + user.getUsername() + "'";
            ResultSet rs = getStatement().executeQuery(sql);
            while (rs.next()) {
                losses = rs.getInt("LOSSES");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return losses;
    }
}
