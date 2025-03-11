/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monopoly.monopoly;

/**
 *
 * @author adnansamore
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Enumeration of possible strategies a player can adopt.
 */
enum Strategy {
    /** A strategy where the player purchasing properties aggressively. */
    GREEDY,

    /** A strategy where the player buys properties cautiously. */
    CAREFUL,

    /** A strategy where the player alternates between buying. */
    TACTICAL
}

/**
 * Represents a player in the game.
 */
public class Player {
    /** The player's name. */
    public String name;

    /** The player's current money balance. */
    public int money = 10000;

    /** The strategy that the player follows for decision-making. */
    Strategy strategy;

    /** The player's current position on the board (default is -1). */
    private int position = -1;

    /** The set of properties the player has visited. */
    private Set<Property> visited = new HashSet<>();

    /** The list of properties that the player owns. */
    List<Property> propertiesOwned = new ArrayList<>();

    /** A flag used to manage the tactical player's turn for purchasing properties. */
    boolean turn = false;

    /**
     * Constructor to initialize a player with a name and a strategy.
     *
     * @param name     The player's name.
     * @param strategy The strategy that the player will follow.
     */
    public Player(String name, Strategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }

    /**
     * Gets the current position of the player on the board.
     *
     * @return The player's current position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the player's position on the board.
     *
     * @param position The new position of the player.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Checks if the player has enough money to pay a specified amount.
     *
     * @param amount The amount to check against the player's money.
     * @return {@code true} if the player has enough money, {@code false} otherwise.
     */
    boolean hasEnufMoney(int amount) {
        return amount <= money;
    }

    /**
     * Allows the player to purchase a property if the conditions for their strategy are met.
     *
     * @param property The property the player wants to purchase.
     */
    void purchaseProperties(Property property) {
        if (property.isOwned()) return;  // Return if the property is already owned

        if (strategy == Strategy.GREEDY && hasEnufMoney(property.price)) {
            this.money -= property.price; // Deduct the price from the player's money
            property.owner = this;
            propertiesOwned.add(property);
        } else if (strategy == Strategy.CAREFUL && hasEnufMoney(property.price) && property.price <= money / 2) {
            this.money -= property.price;
            property.owner = this;
            propertiesOwned.add(property);
        } else if (strategy == Strategy.TACTICAL && hasEnufMoney(property.price) && !turn) {
            this.money -= property.price;
            property.owner = this;
            propertiesOwned.add(property);
        }

        if (strategy == Strategy.TACTICAL) turn = !turn; // Tactical player skips the next buying turn
    }

    /**
     * Checks if the player has visited a specific property before.
     *
     * @param property The property to check.
     * @return {@code true} if the player has visited the property, {@code false} otherwise.
     */
    public boolean hasVisited(Property property) {
        return visited.contains(property);
    }

    /**
     * Marks a property as visited by the player. Once a property is visited, 
     * the player can build a house on it during the next visit.
     *
     * @param property The property to mark as visited.
     */
    public void markVisited(Property property) {
        visited.add(property);
    }

    /**
     * Allows the player to build a house on a property they own, if they have enough money 
     * and the house is not already built.
     *
     * @param property The property where the house will be built.
     */
    public void buildHouse(Property property) {
        if (property.owner == this && !property.ishouse && hasEnufMoney(property.housePrice)) {
            this.money -= property.housePrice;
            property.ishouse = true;
            System.out.println(name + " built a house.");
        }
    }
}

