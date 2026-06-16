/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id; //neo pedio ID
    private String name;
    private double costPerNight;

    // enhmeromenos Constructor
    public City(int id, String name, double costPerNight) {
        this.id = id;
        this.name = name;
        this.costPerNight = costPerNight;
    }

    public int getId() { // nea method getter gia to id
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getCost() {
        return this.costPerNight;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Πόλη: " + name + " (" + costPerNight + "€/night)"; //allagh sto method to string gia na tupwnetai to id sthn othonh 
    }
}
