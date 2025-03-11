/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectFour;

/**
 *
 * @author adnansamore
 */
public class Board {
    private char[][] grid;
    private int rows;
    private int columns;

    /**
     * Initializes the game board with specified rows and columns.
     * Each cell is initially set to an empty space (' ').
     *
     * @param rows the number of rows in the grid
     * @param columns the number of columns in the grid
     */
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = ' ';
            }
        }
        
    }

    /**
     * The disc occupies the lowest available row within the column.
     * @param column the column where the disc is dropped
     * @param disc the character representing the player's disc ('X' or 'O')
     * @return true if the disc was successfully placed; false if the column is full
     */

    public boolean dropDisc(int column, char disc) {
        for (int i = rows - 1; i >= 0; i--) {
            if (grid[i][column] == ' ') {
                grid[i][column] = disc;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the board is fully occupied with discs.
     *
     * @return true if no more moves are possible; false if there are empty spaces
     */
    public boolean isFull() {
        for (int i = 0; i < columns; i++) {
            if (grid[0][i] == ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a player with the specified disc has won the game.
     * win occurs when four consecutive discs align horizontally, vertically, or diagonally.
     * @param disc the character representing the player's disc ('X' or 'O')
     * @return true if the player has four consecutive discs in any direction; false otherwise
     */
    public boolean Winner(char disc) {
       
        // Horizontal
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (grid[i][j] == disc && grid[i][j + 1] == disc &&
                    grid[i][j + 2] == disc && grid[i][j + 3] == disc) {
                    return true;
                }
            }
        }
        // Vertical
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == disc && grid[i + 1][j] == disc &&
                    grid[i + 2][j] == disc && grid[i + 3][j] == disc) {
                    return true;
                }
            }
        }
        // Diagonal (top-left to bottom-right)
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (grid[i][j] == disc && grid[i + 1][j + 1] == disc &&
                    grid[i + 2][j + 2] == disc && grid[i + 3][j + 3] == disc) {
                    return true;
                }
            }
        }
        // Diagonal (bottom-left to top-right)
        for (int i = 3; i < rows; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (grid[i][j] == disc && grid[i - 1][j + 1] == disc &&
                    grid[i - 2][j + 2] == disc && grid[i - 3][j + 3] == disc) {
                    return true;
                }
            }
        }
        return false;
    }

     /**
     * the current state of the game board grid.
     *
     * @return a 2D character array representing the game board
     */
    public char[][] getGrid() {
        return grid;
    }
}

