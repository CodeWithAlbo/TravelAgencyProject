package travelagency;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "agency_data.dat";

    public static void saveData(ArrayList<City> cities, ArrayList<Customer> customers, ArrayList<PackageTour> packages) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cities);
            oos.writeObject(customers);
            oos.writeObject(packages);
            System.out.println("[Susthma Arxeiwn] Ta dedomena apothikeftikan epitixws sto arxeio " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("[Sfalma Arxeiou] Apotuxia Apothikefshs: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData(ArrayList<City> cities, ArrayList<Customer> customers, ArrayList<PackageTour> packages) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("[Susthma Arxeiwn] Den vrethike prohgoumeno arxeio dedomenwn. Ksekiname me kenes listes.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            cities.addAll((ArrayList<City>) ois.readObject());
            customers.addAll((ArrayList<Customer>) ois.readObject());
            packages.addAll((ArrayList<PackageTour>) ois.readObject());
            System.out.println("[Susthma Arxeiwn] Ta dedomena fortwthikan epitixws apo to arxeio!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Sfalma Arxeiou] Apotuxia anagnwshs arxeiou: " + e.getMessage());
        }
    }
}
