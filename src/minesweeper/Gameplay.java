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
    
    /**
     * Returns true if the button at the indicated index has already been revealed.
     *
     * @return
     */
    public boolean isVisible(int i, int j) {
        return (buttonField[i][j]).getText().equals("?");
    }

    /**
     * Returns true if position in the real field is valid.
     *
     * @param position
     * @return
     */
    public boolean isValidPosition(Position position) {
        return (position.getRow() >= 0 && position.getRow() < realField.getFieldSize()
                && position.getColumn() >= 0 && position.getColumn() < realField.getFieldSize());
    }

    /**
     * Reveal empty tiles in proximity, recursively.
     *
     * @param tile
     */
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

    /**
     * Returns true if board is complete.
     *
     * @return
     */
    public boolean checkWin() {
        // Count number of unknown tiles. 
        int count = 0;
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

    /**
     * Reveal the entire board of the button field.
     */
    public void revealAll() {
        for (int i = 0; i < realField.getFieldSize(); i++) {
            for (int j = 0; j < realField.getFieldSize(); j++) {
                if (buttonField[i][j].getText().equals("?")) {
                    int proximityNum = realField.getField()[i][j].getMinesNear();
                    String number = "" + proximityNum;
                    if (proximityNum == State.MINE.getNumber()) {
                        number = "*";
                    } else if (proximityNum == State.EMPTY.getNumber()) {
                        number = " ";
                    }
                    buttonField[i][j].setText(number);
                    buttonField[i][j].setContentAreaFilled(false);
                }
            }
        }
    }
    
    /**
     * Creates a new User object with the given username
     * @param username
     * @return 
     */
    public User createUser(String username) {
        User newUser = new User(username, 0, 0);
        return newUser;
    }
}
