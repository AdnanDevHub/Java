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
 * Represents a service field in the game where players must pay a service cost.
 */
public class Service extends Field {
    /** The cost to be paid for using the service. */
    int cost;

    /**
     * Constructor that sets the service cost and field type to "Service".
     *
     * @param cost The cost to be paid for the service.
     */
    public Service(int cost) {
        this.type = "Service";
        this.cost = cost;
    }
}


