package travelagency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "agency_data.dat";

    // Methodos gia thn apothikefsh των dynamic listwn sth mnhmh (Serialization)
    public static void saveData(ArrayList<City> cities, ArrayList<Customer> customers, ArrayList<PackageTour> packages) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cities);
            oos.writeObject(customers);
            oos.writeObject(packages);
            System.out.println("[File System] Ta dedomena apothikeftikan epitixws sto " + FILE_NAME);
        } catch (Exception e) {
            System.out.println("[File Error] Sfalma kata thn apothikefsh: " + e.getMessage());
        }
    }

    // Methodos gia thn automath fortosh twn dedomenon kata thn ekinnhsh (Deserialization)
    @SuppressWarnings("unchecked")
    public static void loadData(ArrayList<City> cities, ArrayList<Customer> customers, ArrayList<PackageTour> packages) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[File System] To arxeio " + FILE_NAME + " den vrethike. Ginetai arxikopoihsh...");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<City> loadedCities = (ArrayList<City>) ois.readObject();
            ArrayList<Customer> loadedCustomers = (ArrayList<Customer>) ois.readObject();
            ArrayList<PackageTour> loadedPackages = (ArrayList<PackageTour>) ois.readObject();

            cities.addAll(loadedCities);
            customers.addAll(loadedCustomers);
            packages.addAll(loadedPackages);

            System.out.println("[File System] Fortwsh dedomenwn epitixhs! (Poleis: " + cities.size() + ", Pelates: " + customers.size() + ", Paketa: " + packages.size() + ")");
        } catch (Exception e) {
            System.out.println("[File Error] Sfalma kata th fortwsh: " + e.getMessage());
        }
    }
}
