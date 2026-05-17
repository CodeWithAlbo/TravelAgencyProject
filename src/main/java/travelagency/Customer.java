/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private ArrayList<PackageTour> purchasedPackages; // Ta paketa pou exei agorasei

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.purchasedPackages = new ArrayList<>(); // Arxika h lista einai kenh
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public ArrayList<PackageTour> getPurchasedPackages() { return this.purchasedPackages; }

    // Agora paketou
    public void buyPackage(PackageTour p) {
        this.purchasedPackages.add(p);
    }

    // Ektupwsh agorasmenwn paketwn
    public void printPackages() {
        System.out.println("Pelaths [ID: " + id + ", Onoma: " + name + "]");
        if (purchasedPackages.isEmpty()) {
            System.out.println("  -> Den exei agorasei kanena touristiko paketo akoma.");
        } else {
            System.out.println("  Agorasmena paketa:");
            for (PackageTour p : purchasedPackages) {
                System.out.println("   - " + p.getName() + " (Timh: " + p.getPrice() + "€)");
            }
        }
    }
}
