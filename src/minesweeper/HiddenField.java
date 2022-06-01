package minesweeper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;

/**
 *
 * @author Alessandra
 */
public class HiddenField extends Field {
    // The actual field

    public HiddenField(int fieldSize) {
        super(fieldSize);

        // Fill the field with mines
        this.populateField();
        // Update the board with the proximity numbers
        this.revealBoard();
    }

    /**
     *
     * Populate the actual/hidden field with mines.
     */
    public void populateField() {
        Random rand = new Random();

        int count = 0;
        // a random index of the hiddenField will be chosen
        int i = rand.nextInt(getFieldSize());
        int j = rand.nextInt(getFieldSize());

        while (count < getNumMines()) {
            // ensure the random tile isn't already a mine
            while (getField()[i][j].getMinesNear() == State.MINE.getNumber()) {
                i = rand.nextInt(getFieldSize());
                j = rand.nextInt(getFieldSize());
            }
            //getField()[i][j].setMine(true); // A random tile in the board becomes a mine
            getField()[i][j].setMinesNear(State.MINE.getNumber());

            count++;
        }
    }

    /**
     * Count the number of mines around a tile.
     *
     * @param tile
     * @return
     */
    public int countSurrounding(Tile tile) {
        int count = 0;

        // Count the number of mines around a tile a position off-set the tile.
        // e.g. a tile with row 3 col 4 will count through tiles at row 2-4 col 3-5.
        for (int offsetX = tile.getPosition().getRow() - 1; offsetX <= tile.getPosition().getRow() + 1; offsetX++) {
            for (int offsetY = tile.getPosition().getColumn() - 1; offsetY <= tile.getPosition().getColumn() + 1; offsetY++) {
                if (isValid(offsetX, offsetY)) {
                    if (this.getField()[offsetX][offsetY].getMinesNear() == State.MINE.getNumber()) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     *
     * Updates the hidden field to reveal all the proximity and mines.
     */
    public void revealBoard() {
        // update the proximity of the whole board
        for (int i = 0; i < getFieldSize(); i++) {
            for (int j = 0; j < getFieldSize(); j++) {
                if (getField()[i][j].getMinesNear() != State.MINE.getNumber()) {
                    getField()[i][j].setMinesNear(this.countSurrounding(getField()[i][j]));
                }
            }
        }
    }
}
