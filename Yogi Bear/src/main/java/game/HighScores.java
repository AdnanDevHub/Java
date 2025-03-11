/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.*;

/**
 * This class manages the high scores of the game, including creating the high scores table,
 * inserting new high scores, retrieving the top high scores, and updating the high scores list.
 */
public class HighScores {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/highscores";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Creates the high scores table in the database if it does not exist.
     */
    public static void createTable() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "CREATE TABLE IF NOT EXISTS highscores (" +
                         "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                         "name TEXT NOT NULL," +
                         "score INTEGER NOT NULL)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new high score into the high scores database.
     * 
     * @param name  The name of the player.
     * @param score The score achieved by the player.
     */
    public static void insertHighScore(String name, int score) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "INSERT INTO highscores (name, score) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, score);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the top 10 high scores from the database.
     * 
     * @return A list of the top 10 high scores.
     */
    public static List<HighScore> getTopHighScores() {
        List<HighScore> highScores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "SELECT name, score FROM highscores ORDER BY score DESC LIMIT 10";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                highScores.add(new HighScore(name, score));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    /**
     * Updates the high scores table by keeping only the top 10 highest scores.
     */
    public static void updateHighScores() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String sql = "DELETE FROM highscores WHERE id NOT IN (SELECT id FROM (SELECT id FROM highscores ORDER BY score DESC LIMIT 10) AS temp)";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
