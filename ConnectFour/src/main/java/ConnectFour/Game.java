/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectFour;

/**
 *
 * @author adnansamore
 */

/**
 * Manages the Connect Four game, handling players, moves, and checking game states.
 * Allows for a dynamic board size and manages player turns, winning conditions, and resets.
 */
public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game(int rows, int columns) {
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');
        currentPlayer = player1;
        board = new Board(rows, columns);
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

     /**
     * Switches the active player from the current player to the other player.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    /**
     * Attempts to drop the current player's disc in the specified column.
     *
     * @param column the column index where the disc will be dropped
     * @return true if the disc was successfully dropped; false if the column is full
     */
    public boolean Drop(int column) {
        return board.dropDisc(column, currentPlayer.getDisc());
    }

    /**
     * Checks if the current player has achieved a winning condition.
     *
     * @return true if the current player has won; false otherwise
     */
    public boolean checkWinner() {
        return board.Winner(currentPlayer.getDisc());
    }

    /**
     * Determines if the game has reached a draw, meaning the board is fully occupied.
     *
     * @return true if the board is full with no winner; false otherwise
     */
    public boolean isDraw() {
        return board.isFull();
    }

    /**
     * Resets the game to its initial state with a new empty board and resets the current player to Player 1.
     */
    public void reset() {
        board = new Board(board.getGrid().length, board.getGrid()[0].length);
        currentPlayer = player1;
    }
}
