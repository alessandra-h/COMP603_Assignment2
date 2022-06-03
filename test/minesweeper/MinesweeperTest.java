/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javax.swing.JButton;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alessandra
 */
public class MinesweeperTest {
    
    public MinesweeperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Return true if the game is won.
     */
    @Test
    public void testWin() {
        Gameplay gp = new Gameplay();
        int size = 6;
        HiddenField field = new HiddenField(size);
        JButton[][] buttonField = new JButton[size][size];
        gp.setSize(size);
        gp.setRealField(field);
        gp.setButtonField(buttonField);
        
        // Simulate a win by making the JButton text that have mines as "?"
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field.getField()[i][j].getMinesNear() == State.MINE.getNumber()) {
                    gp.getButtonField()[i][j] = new JButton("?");
                } else {
                    gp.getButtonField()[i][j] = new JButton(""+field.getField()[i][j].getMinesNear());
                }
                
            }
        }
        
        assertTrue(gp.checkWin());
    }
    
    /**
     * Return true if the game is lost.
     */
    @Test
    public void testLost() {
        Gameplay gp = new Gameplay();
        int size = 6;
        HiddenField field = new HiddenField(size);
        gp.setSize(size);
        gp.setRealField(field);
        
        // get the index of the first mine in the field
        int mineIndexI = 0;
        int mineIndexJ = 0;
        
        // Simulate a loss by getting the supposed mine index and checking if it is a mine
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field.getField()[i][j].getMinesNear() == State.MINE.getNumber()) {
                    mineIndexI = i;
                    mineIndexJ = j;
                    break;
                }
            }
            break;
        }
        
        assertTrue(gp.checkMine(mineIndexI, mineIndexJ));
    }
    
    /**
     * Return true if user already exists in the table.
     */
    @Test
    public void testExistingUser() {
        DBUserTable table = new DBUserTable();
        String username = "ilovedogs";
        assertTrue(table.ifUserExists(username));
    }
    
    /**
     * Return false if user doesn't exist in the table.
     */
    @Test
    public void testNonExistingUser() {
        DBUserTable table = new DBUserTable();
        String username = "User101";
        assertFalse(table.ifUserExists(username));
    }
    
    /**
     * Get an existing user's record on the USERS table by getting the current user.
     */
    @Test
    public void testExistingUserRecord() {
        DBUserTable table = new DBUserTable();
        DBUserScores scores = new DBUserScores();
        
        User user = table.getCurrentUser("Johnny");
        int wins = scores.getUserWins(user);
        System.out.println("Wins: "+wins);
        int losses = scores.getUserLosses(user);
        System.out.println("Losses: "+losses);
        
        // according to the current USERS table, Johnny's wins is 3 and losses is 1
        assertEquals(3, wins); 
        assertEquals(1, losses);
    }
}
