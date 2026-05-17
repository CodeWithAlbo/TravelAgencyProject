/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.io.Serializable;
import java.util.ArrayList;

public class PackageTour implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private ArrayList<City> cities = new ArrayList<>(); // Sinthesh: lista me poleis
    private ArrayList<Integer> nights = new ArrayList<>(); // Lista me dianukterefseis ana polh
    private ArrayList<Travel> travels = new ArrayList<>(); // Polumorfikh sullogh metakinisewn
    private double profitPercentage;

    public PackageTour(int id, String name, double profitPercentage) {
        this.id = id;
        this.name = name;
        this.profitPercentage = profitPercentage;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }

    public void addCity(City city, int numNights) {
        this.cities.add(city);
        this.nights.add(numNights);
    }

    public void addTravel(Travel travel) {
        this.travels.add(travel);
    }

    // Ypologismos kostous ( diamonh + metakinhseis )
    public double getCost() {
        double total = 0;
        for (int i = 0; i < cities.size(); i++) {
            total += cities.get(i).getCost() * nights.get(i);
        }
        for (Travel t : travels) {
            total += t.getMovingCost();
        }
        return total;
    }

    // Kathorismos timhs me vash to pososto kerdous
    public double getPrice() {
        return this.getCost() * (1 + this.profitPercentage / 100);
    }

    // Ektipwsh stoixeiwn paketou
    public void printPackage() {
        System.out.println("=== Touristiko paketo: " + name + " (ID: " + id + ") ===");
        System.out.println("Diadromh & Diamones:");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(" • " + cities.get(i).getName() + " -> Dianukterefseis: " + nights.get(i));
        }
        System.out.println("Sundeseis metakinhshs:");
        for (Travel t : travels) {
            t.print(); // Polumorfikh klhsh
        }
        System.out.println("Kostos Paketou: " + getCost() + "€ | Timh pwlhshs: " + getPrice() + "€");
        System.out.println("----------------------------------------");
    }
}