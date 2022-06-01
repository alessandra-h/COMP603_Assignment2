package minesweeper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Alessandra
 */
public enum State {

    EMPTY(0), MINE(-1), UNKNOWN(-2);
    
    private final int number;
    
    State(int number) {
        this.number = number;
    }
    
    public int getNumber() {
        return this.number;
    }

}
