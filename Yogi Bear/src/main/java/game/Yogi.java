package game;

/**
 * Represents Yogi Bear in the game with attributes for position, baskets collected, and lives.
 */
public class Yogi {
    private int x, y;
    private int baskets;
    private int lives;

    /**
     * Initializes Yogi's position and sets the initial basket count and lives.
     *
     * @param x The initial x-coordinate of Yogi.
     * @param y The initial y-coordinate of Yogi.
     */
    public Yogi(int x, int y) {
        this.x = x;
        this.y = y;
        this.baskets = 0;
        this.lives = 3;
    }

    /**
     * Moves Yogi by the specified deltas.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
     * Decreases Yogi's life by 1.
     */
    public void decLives() {
        this.lives--;
    }

    /**
     * Gets Yogi's current x-coordinate.
     *
     * @return The x-coordinate of Yogi.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Yogi's current y-coordinate.
     *
     * @return The y-coordinate of Yogi.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the number of baskets Yogi has collected.
     *
     * @return The number of baskets.
     */
    public int getBaskets() {
        return baskets;
    }

    /**
     * Gets the number of lives Yogi has remaining.
     *
     * @return The number of lives.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of baskets Yogi has collected.
     *
     * @param n The new number of baskets.
     */
    public void setBaskets(int n) {
        this.baskets = n;
    }

    /**
     * Sets the number of lives Yogi has remaining.
     *
     * @param n The new number of lives.
     */
    public void setLives(int n) {
        this.lives = n;
    }

    /**
     * Checks if Yogi is alive.
     *
     * @return true if Yogi has lives remaining, false otherwise.
     */
    public boolean isAlive() {
        return lives > 0;
    }

    /**
     * Increments the number of baskets collected by Yogi.
     */
    public void incBaskets() {
        this.baskets++;
    }

    /**
     * Sets Yogi's position on the grid.
     *
     * @param x The new x-coordinate.
     * @param y The new y-coordinate.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
