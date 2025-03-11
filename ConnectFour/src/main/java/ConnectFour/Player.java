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
 * Represents a player in the Connect Four game.
 * Each player has a unique name and a disc symbol (X or O).
 * The player details are used to distinguish between the two competitors
 * in the game.
 * 
 */
public class Player {
    private String name;
    private  final char disc;

    /**
     * Constructs a Player with the specified name and disc symbol.
     *
     * @param name the name of the player
     * @param disc the character representing the player's disc ('X' or 'O')
     */
    public Player(String name, char disc) {
        this.name = name;
        this.disc = disc;
    }

    public String getName() {
        return name;
    }

    public char getDisc() {
        return disc;
    }
}

