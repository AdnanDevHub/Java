package game;

/**
 * Main entry point for the Yogi Bear game.
 * Initializes the game logic and starts the GUI.
 */
public class Game {

    /**
     * The main method that sets up the game logic and the GUI.
     * It loads the level and starts the game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic("levels/level1.txt");
        new GameGUI(gameLogic);
    }
}
