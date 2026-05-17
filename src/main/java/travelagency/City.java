/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private double costPerNight;

    // arxikopoihsh me olh th plhroforia
    public City(String name, double costPerNight) {
        this.name = name;
        this.costPerNight = costPerNight;
    }

    // anaktisi onomatos
    public String getName() {
        return this.name;
    }

    // anaktisi kostous dianikterefsis
    public double getCost() {
        return this.costPerNight;
    }

    @Override
    public String toString() {
        return "City : " + name + " (" + costPerNight + "€/night)";
    }
}