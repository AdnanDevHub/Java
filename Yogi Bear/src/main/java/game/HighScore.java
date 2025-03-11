/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 * Represents a high score entry in the leaderboard.
 * Stores the name of the player and their score.
 */
class HighScore {
    private String name;
    private int score;

    /**
     * Constructs a HighScore object with the player's name and score.
     * @param name The name of the player.
     * @param score The score achieved by the player.
     */
    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the score of the player.
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }
}
