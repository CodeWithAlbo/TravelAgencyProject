package travelagency;

import java.io.Serializable;

public abstract class Travel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected City fromCity;
    protected City toCity;
    protected double cost;

    public Travel(City fromCity, City toCity, double cost) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.cost = cost;
    }

    public double getMovingCost() {
        return this.cost;
    }

    public abstract void print();
}

class AirFlight extends Travel {
    private String airline;

    public AirFlight(City fromCity, City toCity, double cost, String airline) {
        super(fromCity, toCity, cost);
        this.airline = airline;
    }

    @Override
    public void print() {
        System.out.println("[Aeroplano - " + airline + "] " + fromCity.getName() + " -> " + toCity.getName() + " | Kostos: " + cost + "$");
    }
}

class Boat extends Travel {
    private String boatName;

    public Boat(City fromCity, City toCity, double cost, String boatName) {
        super(fromCity, toCity, cost);
        this.boatName = boatName;
    }

    @Override
    public void print() {
        System.out.println("[Ploio - " + boatName + "] " + fromCity.getName() + " -> " + toCity.getName() + " | Kostos: " + cost + "$");
    }
}

class Train extends Travel {
    public Train(City fromCity, City toCity, double cost) {
        super(fromCity, toCity, cost);
    }

    @Override
    public void print() {
        System.out.println("[Treno] " + fromCity.getName() + " -> " + toCity.getName() + " | Kostos: " + cost + "$");
    }
}

class Bus extends Travel {
    public Bus(City fromCity, City toCity, double cost) {
        super(fromCity, toCity, cost);
    }

    @Override
    public void print() {
        System.out.println("[Lewforeio] " + fromCity.getName() + " -> " + toCity.getName() + " | Kostos: " + cost + "$");
    }
}
