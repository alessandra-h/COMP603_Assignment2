package minesweeper;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// maybe i could make this abstract separating the two tiles
/**
 *
 * @author Alessandra
 */
abstract class Field {

    private Tile[][] field;
    private final int fieldSize; // this will also determine the difficulty
    private final int numMines; // relative to the fieldSize

    Field(int fieldSize) {
        this.fieldSize = fieldSize;
        numMines = fieldSize;

        // fill the board with tile instances
        initialise();
    }

    public Tile[][] getField() {
        return field;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public int getNumMines() {
        return numMines;
    }

    /**
     * Checks if row and column is valid in the field.
     */
    public boolean isValid(int row, int column) {
        return (row >= 0 && row < fieldSize
                && column >= 0 && column < fieldSize);
    }

    /**
     * Initialize field with tile objects.
     */
    public void initialise() {
        field = new Tile[fieldSize][fieldSize];

        // i and j represent the index of the 2d array
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                field[i][j] = new Tile(new Position(i, j));
            }
        }
    }

    /**
     * String representation of the field.
     */
    @Override
    public String toString() {
        String output = "     ";
        for (int i = 0; i < fieldSize; i++) {
            output += String.format("%-3s", i);
        }
        output += "\n";

        int colCount = 0;
        for (Tile[] tileRow : field) {
            output += String.format("%3s ", colCount);
            for (Tile tile : tileRow) {
                output += tile;
            }
            output += "\n";
            colCount++;
        }

        return output;
    }

}
