package travelagency;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private ArrayList<PackageTour> purchasedPackages; 

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        this.purchasedPackages = new ArrayList<>(); 
    }

    public String getId() { return this.id; }
    public String getName() { return this.name; }
    public ArrayList<PackageTour> getPurchasedPackages() { return this.purchasedPackages; }

    public void buyPackage(PackageTour p) {
        this.purchasedPackages.add(p);
    }

    public void printPackages() {
        System.out.println("Pelaths [ID: " + id + ", Onoma: " + name + "]");
        if (purchasedPackages.isEmpty()) {
            System.out.println("  -> Den exei agorasei kanena touristiko paketo akoma.");
        } else {
            System.out.println("  Agorasmena paketa:");
            for (PackageTour p : purchasedPackages) {
                System.out.println("   - " + p.getName() + " (Timh: " + p.getPrice() + "$)");
            }
        }
    }
}
