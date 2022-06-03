/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alessandra
 */
public class DBUserTable {
    // For table creation and user creation and getting the current user
    
    private final DBConnection dbConnection;
    private final Connection connection;
    private Statement statement;

    public DBUserTable() {
        dbConnection = new DBConnection(); // establish connection
        connection = dbConnection.getConnection();
        createUsersTable();
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    /**
     * Create table of users.
     */
    public void createUsersTable() {
        try {
            // if the table doesn't already exist, create the table
            if (!ifTableExists("USERS")) {
                statement = connection.createStatement();
                statement.addBatch("CREATE TABLE USERS (USERNAME VARCHAR(16), WINS INT, LOSSES INT)");
                // adding other users as examples of the users table
                statement.addBatch("INSERT INTO USERS VALUES ('Johnny', 3, 1),\n"
                        + "('JESSIE2000', 0, 2)\n,"
                        + "('ilovedogs', 5, 3)");
                statement.executeBatch();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Add user to the USER table
     *
     * @param user
     */
    public void addUserToTable(User user) {
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO USERS VALUES ('" + user.getUsername() + "', " + user.getWins() + ", " + user.getLosses() + ")";
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns true if the table already exists
     *
     * @param tableName
     * @return
     */
    public boolean ifTableExists(String tableName) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                if (table_name.equalsIgnoreCase(tableName)) {
                    System.out.println("USERS table already exists...");
                    return true;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     * Checks if the user already exists in the user table
     *
     * @param username
     * @return
     */
    public boolean ifUserExists(String username) {
        try {
            statement = connection.createStatement();
            String SQL = "SELECT USERNAME FROM USERS WHERE USERNAME = '" + username + "'";
            ResultSet rs = statement.executeQuery(SQL);
            // if the result set has a value this means that user already exists
            if (rs.next()) {
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    /**
     * Return the user given they exist in the USERS table
     * @param username
     * @return 
     */
    public User getCurrentUser(String username) {
        User user = null;

        try {
            statement = connection.createStatement();
            String SQL = "SELECT * FROM USERS WHERE USERNAME = '" + username + "'";
            ResultSet rs = statement.executeQuery(SQL);
            while (rs.next()) {
                String name = rs.getString("USERNAME");
                int wins = rs.getInt("WINS");
                int losses = rs.getInt("LOSSES");
                // make user as the existing user
                user = new User(name, wins, losses);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }
    
    public boolean closeDB() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed!");
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        return false;
    }
}
