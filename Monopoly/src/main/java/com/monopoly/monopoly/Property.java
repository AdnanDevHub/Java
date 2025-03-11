/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monopoly.monopoly;

/**
 *
 * @author adnansamore
 */
/**
 * Represents a property field in the game.
 */
class Property extends Field {
    /** The owner of the property. If null, the property is unowned. */
    Player owner = null;

    /** Flag to check if there is a house built on the property. */
    boolean ishouse = false;

    /** The price of the property. */
    int price = 1000;

    /** The cost to build a house on the property. */
    int housePrice = 4000;

    /**
     * Default constructor that sets the field type to "Property".
     */
    public Property() {
        this.type = "Property";
    }

    /**
     * Checks if the property is owned by a player.
     *
     * @return {@code true} if the property has an owner, {@code false} otherwise.
     */
    boolean isOwned() {
        return owner != null;
    }
}
