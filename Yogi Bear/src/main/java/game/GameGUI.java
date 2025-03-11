package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * the graphical user interface (GUI) of the Yogi Bear game.
 * Manages game window, stats, timers, user input, and game actions.
 */
public class GameGUI {

    private JFrame frame;
    private static GameLogic gameLogic;
    private JPanel gamePanel;
    private static JLabel basketsLabel;
    private static JLabel livesLabel;
    private static JLabel levelTimerLabel;
    private static JLabel levelNumLabel;
    private static Timer gameTimer;
    private static Timer levelTimer;
    private int levelElapsedTime = 0;

    private Image background, yogi, ranger, basket, tree, mountain;

    /**
     * Initializes the game GUI with the given game logic.
     * @param gameLogic the game logic to interact with the game state
     */
    public GameGUI(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        HighScores.createTable();
        loadImages();

        initGameTimer();
        initLevelTimer();

        frame = new JFrame("Yogi Bear");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLevel(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(gameLogic.getLevelGrid()[0].length * 80, gameLogic.getLevelGrid().length * 80));
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);

        JPanel statsPanel = new JPanel(new BorderLayout());

        JPanel labelsPanel = new JPanel(new FlowLayout());
        basketsLabel = new JLabel("Baskets: 0");
        livesLabel = new JLabel("Lives: 3");
        levelTimerLabel = new JLabel("Time: 0 sec");
        levelNumLabel = new JLabel("Level: 1");
        labelsPanel.add(basketsLabel);
        labelsPanel.add(livesLabel);
        labelsPanel.add(levelNumLabel);
        labelsPanel.add(levelTimerLabel);

        statsPanel.add(labelsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> togglePause());
        buttonsPanel.add(pauseButton);

        JButton rulesButton = new JButton("Rules");
        rulesButton.addActionListener(e -> rules());
        buttonsPanel.add(rulesButton);

        statsPanel.add(buttonsPanel, BorderLayout.EAST);

        frame.getContentPane().add(statsPanel, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> restartGame());
        gameMenu.add(restartItem);
        
        JMenuItem highScoreItem = new JMenuItem("High Scores");
        highScoreItem.addActionListener(e -> highScores());
        gameMenu.add(highScoreItem);

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(quitItem);

        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isPaused) return;
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_W) {
                    gameLogic.moveYogi(-1, 0);
                } else if (keyCode == KeyEvent.VK_A) {
                    gameLogic.moveYogi(0, -1);
                } else if (keyCode == KeyEvent.VK_S) {
                    gameLogic.moveYogi(1, 0);
                } else if (keyCode == KeyEvent.VK_D) {
                    gameLogic.moveYogi(0, 1);
                }
                updateStats();
                checkGameOver();
                checkLevelCompletion();
                gamePanel.repaint();
            }
        });

        frame.pack();
        frame.setVisible(true);
        gamePanel.requestFocusInWindow();
    }

    /**
     * Initializes the game timer to move rangers and update stats periodically.
     */
    private void initGameTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }

        gameTimer = new Timer(900, e -> {
            gameLogic.moveRangers();
            updateStats();
            gamePanel.repaint();
        });
        gameTimer.start();
    }

    /**
     * Initializes the level timer to track elapsed time for the current level.
     */
    private void initLevelTimer() {
        if (levelTimer != null) {
            levelTimer.stop();
        }

        levelElapsedTime = 0;
        levelTimer = new Timer(1000, e -> {
            levelElapsedTime++;
            updateLevelTimeLabel();
        });
        levelTimer.start();
    }

    /**
     * Updates the level timer label with the current elapsed time.
     */
    private void updateLevelTimeLabel() {
        levelTimerLabel.setText("Time: " + levelElapsedTime + " sec");
    }

    /**
     * Draws the game level with objects and characters.
     * @param g the Graphics object to draw on
     */
    private void drawLevel(Graphics g) {
        char[][] levelGrid = gameLogic.getLevelGrid();
        g.drawImage(background, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), gamePanel);
        for (int i = 0; i < levelGrid.length; i++) {
            for (int j = 0; j < levelGrid[i].length; j++) {
                char cell = levelGrid[i][j];
                if (cell == 'T') {
                    g.drawImage(tree, j * 80, i * 80, 80, 80, gamePanel);
                } else if (cell == 'M') {
                    g.drawImage(mountain, j * 80, i * 80, 80, 80, gamePanel);
                } else if (cell == 'B') {
                    g.drawImage(basket, j * 80, i * 80, 80, 80, gamePanel);
                } else if (cell == 'Y') {
                    g.drawImage(yogi, j * 80, i * 80, 80, 80, gamePanel);
                } else if (cell == 'R') {
                    g.drawImage(ranger, j * 80, i * 80, 80, 80, gamePanel);
                }
            }
        }
    }

    /**
     * Restarts the game by resetting the game state and timers.
     */
    private void restartGame() {
        gameLogic.restart();
        updateStats();
        initGameTimer();
        initLevelTimer();
        gamePanel.repaint();
        gamePanel.requestFocusInWindow();
    }

    /**
     * Checks if the game is over (when Yogi runs out of lives).
     */
    public static void checkGameOver() {
        if (gameLogic.getYogi().getLives() <= 0) {
            stopTimers();
        }
    }

    /**
     * Checks if the level is completed (all baskets collected).
     */
    private void checkLevelCompletion() {
        if (gameLogic.areAllBasketsCollected()) {
            stopTimers();
            updateStats();
            JOptionPane.showMessageDialog(frame, "Level Complete! Loading next level...");
            gameLogic.loadNextLevel();
            initGameTimer();
            initLevelTimer();
            updateStats();
            gamePanel.requestFocusInWindow();
            gamePanel.repaint();
        }
    }

    /**
     * Stops both the game and level timers.
     */
    public static void stopTimers() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (levelTimer != null) {
            levelTimer.stop();
        }
    }

    private boolean isPaused = false;

    /**
     * Toggles the pause state of the game.
     */
    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            stopTimers();
        } else {
            gameTimer.start();
            levelTimer.start();
        }
        gamePanel.requestFocusInWindow();
    }

    /**
     * Updates the stats displayed on the UI (baskets, lives, and level number).
     */
    public static void updateStats() {
        basketsLabel.setText("Baskets: " + gameLogic.getYogi().getBaskets());
        livesLabel.setText("Lives: " + gameLogic.getYogi().getLives());
        levelNumLabel.setText("Level: " + gameLogic.getLevel());
    }

    /**
     * Displays the game rules in a dialog.
     */
    private void rules() {
        JOptionPane.showMessageDialog(null,
                "Game Rules:\n1. Use WASD to move Yogi.\n2. Collect all baskets.\n3. Avoid Rangers.\n" +
                "4. Yogi loses a life if collides with Ranger\n5. Yogi respawns at the entrance with each life lost.");
    }

    /**
     * Loads the images for the game (background, characters, objects).
     */
    private void loadImages() {
        try {
            background = new ImageIcon(getClass().getResource("/screen.png")).getImage();
            yogi = new ImageIcon(getClass().getResource("/yogi.png")).getImage();
            ranger = new ImageIcon(getClass().getResource("/ranger.png")).getImage();
            basket = new ImageIcon(getClass().getResource("/basket.png")).getImage();
            tree = new ImageIcon(getClass().getResource("/tree.png")).getImage();
            mountain = new ImageIcon(getClass().getResource("/mountain.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading images: " + e.getMessage());
        }
    }

    /**
     * Displays the high scores dialog.
     */
    private void highScores() {
        gameLogic.highScores();
    }
}
