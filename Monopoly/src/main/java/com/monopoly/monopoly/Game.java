/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monopoly.monopoly;

/**
 *
 * @author adnansamore
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the core game logic that manages players, the game board, dice rolls,
 * and player interactions with the game fields.
 */
public class Game {
    /** The game board, consisting of different types of fields. */
    List<Field> board = new ArrayList<>();

    /** List of players participating in the game. */
    List<Player> players = new ArrayList<>();

    /** Stores each round's dice rolls. */
    private List<List<Integer>> diceRolls = new ArrayList<>();

    /** The number of rounds to be played in the game. */
    private int rounds;

    /** Records players who have gone bankrupt. */
    private List<Player> bankrupt = new ArrayList<>();

    /**
     * Loads the game configuration from a specified file, including board fields, players,
     * rounds, and dice rolls.
     *
     * @param fileName The name of the file containing the game configuration.
     * @throws IOException If an I/O error occurs while reading the file.
     * @throws IllegalArgumentException If any of the file contents contain invalid or
     *                                  negative values for fields, players, or rounds.
     */
    public void Load(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        // Load number of fields
        int numofFields = Integer.parseInt(reader.readLine());
        if (numofFields <= 0) {
            throw new IllegalArgumentException("Number of fields must be > 0");
        }

        // Load field data
        for (int i = 0; i < numofFields; i++) {
            String[] data = reader.readLine().split(" ");

            switch (data[0]) {
                case "Property":
                    board.add(new Property());
                    break;
                case "Service":
                    board.add(new Service(Integer.parseInt(data[1])));
                    break;
                case "Lucky":
                    board.add(new Lucky(Integer.parseInt(data[1])));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field type " + data[0]);
            }
        }

        // Load player data
        int numofPlayers = Integer.parseInt(reader.readLine());
        if (numofPlayers <= 0) {
            throw new IllegalArgumentException("Number of Players must be > 0");
        }
        for (int i = 0; i < numofPlayers; i++) {
            String[] playerData = reader.readLine().split(" ");
            String playerName = playerData[0];
            Strategy strategy = Strategy.valueOf(playerData[1].toUpperCase());
            players.add(new Player(playerName, strategy));
        }

        // Load number of rounds
        rounds = Integer.parseInt(reader.readLine().trim());
        if (rounds <= 0) {
            throw new IllegalArgumentException("Number of rounds must be > 0");
        }

        // Load dice rolls
        for (int i = 0; i < rounds; i++) {
            String[] rolls = reader.readLine().trim().split(" ");
            List<Integer> roundRolls = new ArrayList<>();
            for (String roll : rolls) {
                int dice = Integer.parseInt(roll.trim());
                if (dice <= 0) {
                    throw new IllegalArgumentException("Dice roll must be > 0");
                }
                roundRolls.add(dice);
            }
            diceRolls.add(roundRolls); // Add the dice rolls for this round to the list
        }
    }

    /**
     * Starts the game and simulates each round. Players roll dice, move to new positions,
     * and interact with the fields on the board. Handles buying properties, paying rent,
     * and checking for bankruptcy.
     */
    public void Play() {
        for (int round = 1; round <= rounds; round++) {
            System.out.println("Round === " + round);
            List<Integer> currentRolls = diceRolls.get(round - 1);
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);

                // Each player gets their respective roll for the current round
                int roll = currentRolls.get(i);
                int currentPosition = player.getPosition();
                int playerPosition = (currentPosition + roll) % board.size(); // Calculate new position (cyclic)

                System.out.println(player.name + " rolls " + roll + " and moves from " + currentPosition +
                        " to position " + playerPosition);

                // Update player position
                player.setPosition(playerPosition);

                // Get the field the player landed on
                Field field = board.get(playerPosition);

                // Simulate the actions on the field
                Simulation(player, field);

                // Handle bankruptcy
                if (player.money <= 0) {
                    System.out.println(player.name + " is bankrupt.");

                    // Free owned properties
                    for (Property property : player.propertiesOwned) {
                        property.owner = null;
                    }

                    bankrupt.add(player);
                    players.remove(player);
                    i--; // Adjust index to avoid skipping the next player
                }
            }
        }
    }

    /**
     * Simulates the actions a player must take when landing on a field.
     *
     * @param player The player interacting with the field.
     * @param field  The field the player landed on.
     */
    void Simulation(Player player, Field field) {
        if (field instanceof Property) {
            Property property = (Property) field;
            if (property.isOwned() && property.owner != player) {
                // Pay rent
                int rent = property.ishouse ? 2000 : 500;
                player.money -= rent;
                property.owner.money += rent;
                System.out.println(player.name + " paid " + rent + " to " + property.owner.name);
            } else if (!property.isOwned()) {
                player.purchaseProperties(property);
                if (property.isOwned()) {
                    System.out.println(player.name + " bought a property for " + property.price);
                    player.markVisited(property); // Mark the property as visited, so the player can build a house on the next visit
                }
            } else if (property.owner == player && !property.ishouse) {
                if (player.hasVisited(property)) {
                    player.buildHouse(property);
                }
            }
        } else if (field instanceof Service) {
            player.money -= ((Service) field).cost;
            System.out.println(player.name + " paid " + ((Service) field).cost + " to the bank.");
        } else if (field instanceof Lucky) {
            player.money += ((Lucky) field).reward;
            System.out.println(player.name + " received " + ((Lucky) field).reward + " from the lucky field.");
        }
    }

    /**
     * Displays the final status of all players at the end of the game,
     * including the bankrupt players and their remaining properties and money.
     */
    public void FinalStatus() {
        System.out.println("-----FINAL STATUS-----");
        for (Player player : players) {
            System.out.println(player.name + " - Money: " + player.money);
            System.out.println("Properties owned: " + player.propertiesOwned.size());
        }
        for (Player player : bankrupt) {
            System.out.println(player.name + " - Money: " + player.money);
            System.out.println("Properties owned: " + player.propertiesOwned.size());
        }
    }
}
