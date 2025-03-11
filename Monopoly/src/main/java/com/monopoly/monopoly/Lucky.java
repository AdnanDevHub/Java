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
 * Represents a lucky field in the game where players can receive a reward.
 */
public class Lucky extends Field {
    /** The reward that a player can receive. */
    int reward;

    /**
     * Constructor that sets the reward and field type to "Lucky".
     *
     * @param reward The reward amount that a player can receive.
     */
    public Lucky(int reward) {
        this.type = "Lucky";
        this.reward = reward;
    }
}
