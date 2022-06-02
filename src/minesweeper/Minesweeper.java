/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

/**
 *
 * @author Alessandra
 */
public class Minesweeper {
    private final MinesweeperGUI gui;
    private final Gameplay gp;
    private final DBUserTable db;
    
    public Minesweeper() {
        gp = new Gameplay();
        db = new DBUserTable();
        gui = new MinesweeperGUI(gp, db);
    }

    public MinesweeperGUI getGui() {
        return gui;
    }

    public Gameplay getGp() {
        return gp;
    }

    public DBUserTable getDb() {
        return db;
    }

    public static void main(String[] args) {
        Minesweeper minesweeper = new Minesweeper();
    }
}
