package game;

/**
 * Represents a Ranger in the game with attributes for position and movement.
 */
public class Ranger {
    private int x;
    private int y;

    /**
     * Initializes the Ranger's position.
     *
     * @param x The initial x-coordinate of the Ranger.
     * @param y The initial y-coordinate of the Ranger.
     */
    public Ranger(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the current x-coordinate of the Ranger.
     *
     * @return The x-coordinate of the Ranger.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the Ranger.
     *
     * @return The y-coordinate of the Ranger.
     */
    public int getY() {
        return y;
    }

    /**
     * Moves the Ranger by the specified deltas.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
