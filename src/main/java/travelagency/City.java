package travelagency;

import java.io.Serializable;

public class City implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id; // monimo id polhs
    private String name;
    private double costPerNight;

    // Constructor me upostiriksi id
    public City(int id, String name, double costPerNight) {
        this.id = id;
        this.name = name;
        this.costPerNight = costPerNight;
    }

    public int getId() {
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
        return "ID: " + id + " | Polh : " + name + " (" + costPerNight + "$/night)";
    }
}
