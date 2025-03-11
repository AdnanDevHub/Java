/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConnectFour;

/**
 *
 * @author adnansamore
 */
import javax.swing.*;
import java.awt.*;

/**
 * ConnectFourGUI is a graphical interface for the Connect Four game.
 * It allows players to interact with the game through a GUI, including dropping discs, resetting the game, and changing board sizes.
 * The class handles user actions and updates the visual representation of the board accordingly.
 */
public class ConnectFourGUI {
    private JFrame frame;
    private JButton[] buttons;
    private JButton resetButton;
    private JLabel[][] gridLabels;
    private Game game;
    private JPanel gridPanel;
    
    // Default grid size
    private final int initRows = 8;
    private final int initColumns = 5;

    public ConnectFourGUI() {
        initGame(initRows, initColumns); 
        
    }

    /**
     * Initializes the game board and GUI elements with the specified rows and columns.
     * Sets up the frame, buttons, grid display, and layout.
     *
     * @param rows the number of rows for the game board
     * @param columns the number of columns for the game board
     */
    private void initGame(int rows, int columns) {
        game = new Game(rows, columns);

        // Frame
        frame = new JFrame("Connect Four");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // MenuBar
        MenuBar();

        // Top Panel for Drop Buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, columns));
        buttons = new JButton[columns];
        for (int i = 0; i < columns; i++) {
            final int col = i;
            buttons[i] = new JButton("Drop");
            buttons[i].setBackground(Color.BLUE);
            buttons[i].setFocusPainted(false);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 16));
            buttons[i].addActionListener(e -> dropDisc(col));
            topPanel.add(buttons[i]);
        }
        frame.add(topPanel, BorderLayout.NORTH);

        // Grid Panel for the Board
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, columns));
        gridLabels = new JLabel[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridLabels[i][j] = new JLabel(" ", SwingConstants.CENTER);
                gridLabels[i][j].setOpaque(true);
                gridLabels[i][j].setBackground(Color.WHITE);
                gridLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridPanel.add(gridLabels[i][j]);
            }
        }
        frame.add(gridPanel, BorderLayout.CENTER);

        // Bottom Panel with Reset Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        resetButton = new JButton("Reset");
        resetButton.setBackground(Color.PINK);
        resetButton.setFocusPainted(false);
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(e -> resetGame());
        bottomPanel.add(resetButton, BorderLayout.EAST);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(900, 700);
        frame.setVisible(true);
    }

    /**
     * Configures the menu bar for the game with options for board size and exit.
     */
    private void MenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setFont(new Font("Arial", Font.BOLD, 18));
        JMenuItem size1 = new JMenuItem("8 x 5");
        JMenuItem size2 = new JMenuItem("10 x 6");
        JMenuItem size3 = new JMenuItem("12 x 7");

        size1.addActionListener(e -> restart(8, 5));
        size2.addActionListener(e -> restart(10, 6));
        size3.addActionListener(e -> restart(12, 7));

        gameMenu.add(size1);
        gameMenu.add(size2);
        gameMenu.add(size3);
        
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setFont(new Font("Arial",Font.BOLD,16));
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(e -> System.exit(0));
       
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
        
    }

     /**
     * Drops a disc in the specified column for the current player and updates the GUI.
     * Checks for a winner or draw, and switches players if the game continues.
     *
     * @param col the column where the disc is dropped
     */
    private void dropDisc(int col) {
        if (game.Drop(col)) {
            updateGrid();
            if (game.checkWinner()) {
                JOptionPane.showMessageDialog(frame, game.getCurrentPlayer().getName() + " wins!");
                resetGame();
            } else if (game.isDraw()) {
                JOptionPane.showMessageDialog(frame, "It's a draw!");
                resetGame();
            } else {
                game.switchPlayer();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Column is full.");
        }
    }

     /**
     * Updates the GUI grid to reflect the current state of the game board.
     */
    private void updateGrid() {
        char[][] grid = game.getBoard().getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                gridLabels[i][j].setText(String.valueOf(grid[i][j]));
                if (grid[i][j] == 'X') {
                    gridLabels[i][j].setForeground(Color.BLACK);
                } else if (grid[i][j] == 'O') {
                    gridLabels[i][j].setForeground(Color.BLUE);
                }
            }
        }
    }

    /**
     * Resets the game board and GUI to start a new game with the same board size.
     */
    private void resetGame() {
        game.reset();
        emptyGrid();
    }
 
    /**
     * Clears all discs from the GUI grid.
     */
    private void emptyGrid() {
    for (JLabel[] row : gridLabels) {            
        for (JLabel columns : row) {                
            columns.setText(" ");
            columns.setBackground(Color.WHITE);
        }
    }
}

    /**
     * Restarts the game with a new board of the specified size.
     *
     * @param rows the number of rows for the new game board
     * @param cols the number of columns for the new game board
     */
    private void restart(int rows, int cols) {
        frame.dispose();
        initGame(rows, cols);
    }
    
}



