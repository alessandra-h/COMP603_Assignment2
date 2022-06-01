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
public class Tile {

    private Position position;
    private int minesNear;

    public Tile(Position position) {
        this.position = position;
        this.minesNear = State.UNKNOWN.getNumber();
    }

    //----------------------------GETTERS & SETTERS-----------------------------
    public Position getPosition() {
        return position;
    }

    public int getMinesNear() {
        return minesNear;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMinesNear(int minesNear) {
        this.minesNear = minesNear;
    }
    
    //--------------------------------------------------------------------------
    
    @Override
    public String toString() {
        String out = "[";

        if (minesNear == State.UNKNOWN.getNumber()) {
            out+= "?";
        } else if (minesNear == State.MINE.getNumber()) {
            out+= "*";
        } else if (minesNear == State.EMPTY.getNumber()) {
            out += " ";
        } else {
            out += minesNear;
        }
        
        out+= "]";
        
        return out;
    }
}
