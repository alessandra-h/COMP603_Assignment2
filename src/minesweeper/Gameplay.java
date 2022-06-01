/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minesweeper;

import javax.swing.JButton;

/**
 *
 * @author Alessandra
 */
public class Gameplay {
    
    private HiddenField realField;
    private JButton[][] buttonField;
    private int size;

    public JButton[][] getButtonField() {
        return buttonField;
    }

    public HiddenField getRealField() {
        return realField;
    }

    public int getSize() {
        return size;
    }

    public void setButtonField(JButton[][] buttonField) {
        this.buttonField = buttonField;
    }
    
    public void setRealField(HiddenField realField) {
        this.realField = realField;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    // Methods to help play game
    public boolean isVisible(int i, int j) {
        return (buttonField[i][j]).getText().equals("?");
    }

    public boolean isValidPosition(Position position) {
        return (position.getRow() >= 0 && position.getRow() < realField.getFieldSize()
                && position.getColumn() >= 0 && position.getColumn() < realField.getFieldSize());
    }

    public void revealEmptyTiles(int i, int j) {
        for (int offsetX = i - 1; offsetX <= i + 1; offsetX++) {
            for (int offsetY = j - 1; offsetY <= j + 1; offsetY++) {
                if (isValidPosition(new Position(offsetX, offsetY))) {;
                    if (isVisible(offsetX, offsetY)) { // this is to prevent infinite recursion
                        // If the tile of the real field is empty, reveal/set the button field tile as empty
                        // and then call the method again for the empty buttons
                        if (realField.getField()[offsetX][offsetY].getMinesNear() == State.EMPTY.getNumber()) {
                            buttonField[offsetX][offsetY].setText("");
                            buttonField[offsetX][offsetY].setContentAreaFilled(false);
                            revealEmptyTiles(offsetX, offsetY); // recursion
                        }
                    }
                }
            }
        }
    }

    public boolean checkWin() {
        // Count number of unknown tiles. 
        int count = -1;
        for (JButton[] buttonRow : buttonField) {
            for (JButton button : buttonRow) {
                if (button.getText().equals("?")) {
                    count++;
                }
            }
        }
        //If it is equal to the number of mines, then player has won.
        return count == realField.getNumMines();
    }

    public void revealAll() {
        for (int i = 0; i < realField.getFieldSize(); i++) {
            for (int j = 0; j < realField.getFieldSize(); j++) {
                if (buttonField[i][j].getText().equals("?")) {
                    int proximityNum = realField.getField()[i][j].getMinesNear();
                    String number = "" + proximityNum;
                    if (proximityNum == -1) {
                        number = "*";
                    } else if (proximityNum == 0) {
                        number = " ";
                    }
                    buttonField[i][j].setText(number);
                    buttonField[i][j].setContentAreaFilled(false);
                }
            }
        }
    }
    
    // public User create user?
}
