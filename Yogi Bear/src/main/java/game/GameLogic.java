/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 * The main logic of the game, including the management of game levels, player movement, and interactions with objects (baskets, rangers).
 * Handles reading level files, managing game state, and processing player actions.
 * Handles moving Yogi and Rangers, detecting collisions, and progressing through levels.
 */
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class GameLogic {
    private char[][] levelGrid;
    private Yogi yogi;
    private List<Ranger> rangers;
    private int currentLevel = 1;
    private final int MAX_LEVELS = 10;
    private int totalBaskets;
    private int totalBasketsCollected = 0;

    /**
     * Initializes the game logic and loads the level based on the provided file path.
     * @param FilePath The path to the level file.
     */
    public GameLogic(String FilePath) {
        loadLevel(FilePath);
    }

    /**
     * Loads the level grid from a file, initializes Yogi and Rangers, and counts the total baskets.
     * @param FilePath The path to the level file.
     */
    private void loadLevel(String FilePath) {
        this.levelGrid = readLevelFile(FilePath);
        this.yogi = findYogi();
        this.rangers = findRangers();
        this.totalBaskets = totalBaskets(); 
    }

    /**
     * Counts the total number of baskets in the level grid.
     * @return The total number of baskets.
     */
    private int totalBaskets() {
        int count = 0;
        for (char[] row : levelGrid) {
            for (char cell : row) {
                if (cell == 'B') {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Reads the level file and converts it into a 2D character grid.
     * @param filePath The path to the level file.
     * @return A 2D character array representing the level grid.
     */
    private char[][] readLevelFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/" + filePath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    /**
     * Finds the position of Yogi in the level grid.
     * @return The Yogi object representing the player.
     */
    private Yogi findYogi() {
        for (int i = 0; i < levelGrid.length; i++) {
            for (int j = 0; j < levelGrid[i].length; j++) {
                if (levelGrid[i][j] == 'Y') {
                    return new Yogi(i, j);
                }
            }
        }
        return null;
    }

    /**
     * Finds all Rangers in the level grid.
     * @return A list of Ranger objects.
     */
    private List<Ranger> findRangers() {
        List<Ranger> rangers = new ArrayList<>();
        for (int i = 0; i < levelGrid.length; i++) {
            for (int j = 0; j < levelGrid[i].length; j++) {
                if (levelGrid[i][j] == 'R') {
                    rangers.add(new Ranger(i, j));
                }
            }
        }
        return rangers;
    }
    
    /**
     * Gets the current level of the game.
     * @return The current level number.
     */
    public int getLevel(){
        return currentLevel;
    }

    /**
     * Gets the current level grid.
     * @return The 2D character array representing the level grid.
     */
    public char[][] getLevelGrid() {
        return levelGrid;
    }

    /**
     * Gets the Yogi object representing the player.
     * @return The Yogi object.
     */
    public Yogi getYogi() {
        return yogi;
    }

    /**
     * Moves all Rangers randomly within the level grid.
     * If a Ranger collides with Yogi, Yogi loses a life.
     */
    public void moveRangers() {
        Random random = new Random();
        for (Ranger ranger : rangers) {
            int direction = random.nextInt(2);
            int dx = 0, dy = 0;

            if (direction == 0) {
                int horizontalDirection = random.nextInt(2);
                if (horizontalDirection == 0) {
                    dx = -1;
                } else {
                    dx = 1;
                }
            } else {
                int verticalDirection = random.nextInt(2);
                if (verticalDirection == 0) {
                    dy = -1;
                } else {
                    dy = 1;
                }
            }

            int newX = ranger.getX() + dx;
            int newY = ranger.getY() + dy;
            if (newX >= 0 && newX < levelGrid.length && newY >= 0 && newY < levelGrid[0].length) {
                if (levelGrid[newX][newY] != 'M' && levelGrid[newX][newY] != 'T' && levelGrid[newX][newY] != 'R' && levelGrid[newX][newY] != 'B') {
                    levelGrid[ranger.getX()][ranger.getY()] = '.';
                    ranger.move(dx, dy);
                    levelGrid[newX][newY] = 'R';

                    if (newX == yogi.getX() && newY == yogi.getY()) {
                        yogi.decLives();
                        if (yogi.isAlive()) {
                            respawnYogi();
                        } else {
                            GameGUI.updateStats();
                            GameGUI.checkGameOver();
                            gameOver();
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Moves Yogi by the specified amount.
     * @param dx The change in X position.
     * @param dy The change in Y position.
     */
    public void moveYogi(int dx, int dy) {
        int newX = yogi.getX() + dx;
        int newY = yogi.getY() + dy;

        if (newX >= 0 && newX < levelGrid.length && newY >= 0 && newY < levelGrid[0].length) {
            if (levelGrid[newX][newY] == '.' || levelGrid[newX][newY] == 'B') {
                levelGrid[yogi.getX()][yogi.getY()] = '.';

                if (levelGrid[newX][newY] == 'B') {
                    yogi.incBaskets();
                    levelGrid[newX][newY] = '.';
                }

                yogi.move(dx, dy);
                levelGrid[newX][newY] = 'Y';
            } else if (levelGrid[newX][newY] == 'R') {
                yogi.decLives();

                if (yogi.getLives() > 0) {
                    respawnYogi();
                } else {
                    GameGUI.updateStats();
                    GameGUI.checkGameOver();
                    gameOver();
                }
          }
        }
    }

    /**
     * Respawns Yogi at the initial position (0, 0) on the grid.
     */
    public void respawnYogi() {
        levelGrid[yogi.getX()][yogi.getY()] = '.';
        yogi.setPosition(0, 0);
        levelGrid[0][0] = 'Y';
    }

    /**
     * Restarts the game by resetting the level and Yogi's state.
     */
    public void restart() {
        currentLevel = 1;
        yogi.setLives(3);
        yogi.setBaskets(0);
        totalBasketsCollected = 0;
        levelGrid = readLevelFile("levels/level" + currentLevel + ".txt");
        this.yogi = findYogi();
        this.rangers = findRangers();
        this.totalBaskets = totalBaskets();
    }

    /**
     * Checks if all baskets have been collected by Yogi.
     * @return True if all baskets are collected, false otherwise.
     */
    public boolean areAllBasketsCollected() {
        return yogi.getBaskets() == totalBaskets;
    }

    /**
     * Loads the next level, if available.
     * If all levels are completed, the game is marked as complete.
     */
    public void loadNextLevel() {
        currentLevel++;
        allLevelbaskets();
        if (currentLevel <= MAX_LEVELS) {
            levelGrid = readLevelFile("levels/level" + currentLevel + ".txt");
            yogi = findYogi();
            yogi.setBaskets(0);
            rangers = findRangers();
            totalBaskets = totalBaskets();
        } else {
            completeGame();
        }
    }

    /**
     * Updates the total number of baskets collected across all levels.
     */
    private void allLevelbaskets() {
        totalBasketsCollected += yogi.getBaskets();
    }

    /**
     * Completes the game and asks the player for their name, saving the score.
     */
    private void completeGame() {
        String playerName = JOptionPane.showInputDialog(null, "Congratulations! You've completed all levels!\nEnter your name:");

        if (playerName != null && !playerName.trim().isEmpty()) {
            HighScores.insertHighScore(playerName, totalBasketsCollected);
        }
        JOptionPane.showMessageDialog(null, "Your score is saved " + totalBasketsCollected);
        System.exit(0);
    }

    /**
     * Handles the game over condition and saves the player's score.
     */
    private void gameOver() {
        allLevelbaskets();
        String playerName = JOptionPane.showInputDialog(null, "Game Over! Enter your name:");

        if (playerName != null && !playerName.trim().isEmpty()) {
            HighScores.insertHighScore(playerName, totalBasketsCollected);
        }
        JOptionPane.showMessageDialog(null, "Your score is saved " + totalBasketsCollected);
        System.exit(0);
    }

    /**
     * Displays the leaderboard showing the top high scores.
     */
    public void highScores() {
        List<HighScore> highScores = HighScores.getTopHighScores();
        StringBuilder highScoresText = new StringBuilder("Leaderboard\n");

        for (int i = 0; i < highScores.size(); i++) {
            HighScore highScore = highScores.get(i);
            highScoresText.append(i + 1).append(". ").append(highScore.getName()).append(" - ").append(highScore.getScore()).append("\n");
        }

        JOptionPane.showMessageDialog(null, highScoresText.toString());
    }
}
