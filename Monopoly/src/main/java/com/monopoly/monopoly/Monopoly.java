/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.monopoly.monopoly;

/**
 *
 * @author adnansamore
 */
import java.io.IOException;

public class Monopoly {
    /**
     * The main method serves as the entry point of the Monopoly game.
     * It initializes the game, loads data from a file, and starts the game simulation.
     * 
     * @param args Command-line arguments (not used in this program).
     * @throws IOException If there is an issue loading the game data from the file.
     */

    public static void main(String[] args)throws IOException {
        
       Game game = new Game();
        game.Load("input3.txt");
        game.Play();
        game.FinalStatus();
    }
}
