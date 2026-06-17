/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package travelagency;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Arxikopoihsh ths Scanner gia thn eisodo dedomenwn apo thn konsola
        Scanner scanner = new Scanner(System.nanoTime() > 0 ? System.in : null);
        
        // Dynamic arrays (ArrayLists) gia th dynamic diaxeirish antikeimenwn sth mnhmh (Runtime)
        ArrayList<City> cities = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<PackageTour> packages = new ArrayList<>();

        // 1. FORTOSI DATA: Aftomati fortwsi twn dedomenwn apo to duadiko arxeio (.dat)
        FileManager.loadData(cities, customers, packages);

        // Stathero pososto kerdous gia to taksidhiwtiko grafeio (25%)
        final double agencyProfitMargin = 25.0;

        // 2. MIXANISMOS DEMO: An oles oi listes einai kenes, dhmiourgountai arxika dynamic antikeimena me ID
        if (cities.isEmpty() && packages.isEmpty() && customers.isEmpty()) {
            System.out.println("\n[Demo] Dhmiourgia arxikwn antikeimenwn me ID gia thn epideiksh...");
            
            // Dhmiourgia polewn me vash th nea apaithsh gia ID
            City athens = new City(1, "Athens", 0.0); 
            City rome = new City(2, "Rome", 80.0);
            City paris = new City(3, "Paris", 120.0);
            
            cities.add(athens);
            cities.add(rome);
            cities.add(paris);

            // Dhmiourgia demo paketou (Ylopoihsh ths ennoias ths Syntheshs / Composition)
            PackageTour EuropeTour = new PackageTour(101, "Europe Panorama", agencyProfitMargin);
            EuropeTour.addCity(athens, 0); 
            EuropeTour.addCity(rome, 3);   
            EuropeTour.addCity(paris, 4);  
            EuropeTour.addCity(athens, 0); 

            // Prosthikh polymorfikwn metakinhsewn (Polymorfismos mesw ths abstract Travel)
            Travel flight = new AirFlight(athens, rome, 150.0, "Aegean Airlines");
            Travel train = new Train(rome, paris, 90.0);
            Travel returnFlight = new AirFlight(paris, athens, 180.0, "Air France");
            
            EuropeTour.addTravel(flight);
            EuropeTour.addTravel(train);
            EuropeTour.addTravel(returnFlight);
            
            packages.add(EuropeTour);

            // Kataxwrhsh arxikou pelath kai dynamic agora tou demo paketou
            Customer guest = new Customer("CUST-001", "Elton Paptsi");
            guest.buyPackage(EuropeTour);
            customers.add(guest);
            
            System.out.println("[Demo] Ta arxika dedomena dhmiourgithikan epitixws!\n");
        }

        // 3. SYGXRONISMOS ME MYSQL: Dynamic anaktish polewn apo th MySQL vash twn dynamic IDs mas
        try {
            ArrayList<City> dbCities = DBManager.loadCitiesFromDB();
            if (!dbCities.isEmpty()) {
                cities = dbCities; // Antikatastash ths mnhmhs me ta freska dedomena ths MySQL
            }
        } catch (SQLException e) {
            // Fallback mhxanismos se periptwsh pou h vash SQL einai offline h kleisth
            System.out.println("[MySQL Sync] H vash einai kenh h offline, xrhsh topikwn polewn.");
        }

        // 4. KENTRIKO LOOP TOY MENOY (Krataei thn efarmogh anoixti mexri na paththei to 6)
        int choice = 0;
        while (choice != 6) {
            System.out.println("\n================= VASIKO MENU =================");
            System.out.println("1) Diaxeirhsh Paketou (Prosthikh me Erwthseis / Emfanish / Diagrafh)");
            System.out.println("2) Diaxeirhsh Pelath (CRUD & Epilegmenh Agora Paketou)");
            System.out.println("3) Diaxeirhsh Polhs (Prosthikh me ID / Tropopoihsh / Diagrafh)");
            System.out.println("4) Emfanhsh plithous Pelatwn kai Paketwn");
            System.out.println("5) Ypologismos & Emfanisi stoixeiwn kerdous ana paketo");
            System.out.println("6) Apothikefsh dedomenwn & Eksodos apo thn efarmogh");
            System.out.print("Parakalw Epilekste (1-6): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: // --- DIAXEIRHSH PAKETOU ---
                        System.out.println("\n--- DIAXEIRHSH PAKETOU ---");
                        System.out.println("1. Prosthikh Neou Paketou (Dynamiki Epilogh Polewn)");
                        System.out.println("2. Provolh olwn twn paketwn");
                        System.out.println("3. Diagrafh Paketou (Delete)");
                        System.out.print("Epilogh: ");
                        int pChoice = Integer.parseInt(scanner.nextLine());
                        
                        if (pChoice == 1) { // DYNAMIKI PROSTHIKH PAKETOY ME ERWTHSEIS CODES
                            if (cities.isEmpty()) {
                                System.out.println("Sfalma: Den yparxoun poleis! Prosthiste prwta poleis apo to Menu 3.");
                                break;
                            }
                            System.out.print("Eisagete Kwdiko (ID) Paketou: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            System.out.print("Eisagete Onoma Paketou: ");
                            String name = scanner.nextLine();
                            
                            PackageTour p = new PackageTour(id, name, agencyProfitMargin);
                            
                            // Interactive design loop: O xrhsths eisaegei oses poleis thelei dynamic
                            System.out.println("\n--- EROTHSEIS DIADROMHS ---");
                            while (true) {
                                System.out.println("Diathesimes Poleis:");
                                for (City c : cities) {
                                    System.out.println("  " + c); // To c kalei th methodo toString() ths City
                                }
                                System.out.print("Eisagete to ID ths polhs pou thelete na prosthesete (h 0 gia telos): ");
                                int targetId = Integer.parseInt(scanner.nextLine());
                                if (targetId == 0) break; // Eksodos apo th dynamic prosthikh polewn
                                
                                // Sequential scan loop gia na vrethei h polh me to targetId
                                City selectedCity = null;
                                for (City c : cities) {
                                    if (c.getId() == targetId) {
                                        selectedCity = c;
                                        break;
                                    }
                                }
                                
                                // An h polh yparxei, th syndeoume dynamic sto paketo mazi me tis nuyxtes
                                if (selectedCity != null) {
                                    System.out.print("Eisagete arithmo dianukterefsewn gia thn polh " + selectedCity.getName() + ": ");
                                    int numNights = Integer.parseInt(scanner.nextLine());
                                    p.addCity(selectedCity, numNights);
                                    System.out.println("- H polh prostethike!"); 
                                } else {
                                    System.out.println("Sfalma: Den vrethike polh me ayto to ID!");
                                }
                                System.out.println("----------------------------------------");
                            }
                            
                            // SYNDESH ME MYSQL: Grafei ta stoixeia kai sto neo pinakaki ths vasis data
                            DBManager.insertPackage(id, name, agencyProfitMargin);
                            
                            packages.add(p);
                            System.out.println("To touristiko paketo '" + name + "' ftiaxtike epitixws!");
                        } else if (pChoice == 2) { // PROVOLH
                            if (packages.isEmpty()) {
                                System.out.println("Den uparxoun kataxwrhmena paketa.");
                            } else {
                                for (PackageTour p : packages) {
                                    p.printPackage();
                                }
                            }
                        } else if (pChoice == 3) { // APAITHSH: DIAGRAFH PAKETOU (DELETE)
                            if (packages.isEmpty()) {
                                System.out.println("Den uparxoun paketa gia diagrafh.");
                                break;
                            }
                            System.out.print("Eisagete to ID tou paketou pou thelete na diagrapsete: ");
                            int idToDelete = Integer.parseInt(scanner.nextLine());
                            
                            // Dynamic sequential loop scan gia thn afairesh tou antikeimenou apo th dynamic lista
                            boolean found = false;
                            for (int i = 0; i < packages.size(); i++) {
                                if (packages.get(i).getId() == idToDelete) {
                                    String removedName = packages.get(i).getName();
                                    packages.remove(i); // Diagrafh apo th runtime arraylist ths Java
                                    System.out.println("- To paketo '" + removedName + "' (ID: " + idToDelete + ") diagrafhke epitixws!"); 
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                System.out.println("Sfalma: Den vrethike paketo me ayto to ID!");
                            }
                        }
                        break;

                    case 2: // --- DIAXEIRHSH PELATH (CRUD & INTERACTIVE SYSTEM DESIGN) ---
                        System.out.println("\n--- DIAXEIRHSH PELATH ---");
                        System.out.println("1. Prosthikh Pelath (Create)\n2. Tropopoihsh Pelath (Update)\n3. Diagrafh Pelath (Delete)\n4. KATAXORHSH AGORAS (Epilogh Pelath & Paketou)");
                        System.out.print("Epilogh: ");
                        int cChoice = Integer.parseInt(scanner.nextLine());
                        
                        if (cChoice == 1) { // CREATE CUSTOMER
                            System.out.print("Eisagete ID Pelath: ");
                            String custId = scanner.nextLine();
                            System.out.print("Eisagete Onoma Pelath: ");
                            String custName = scanner.nextLine();
                            
                            DBManager.insertCustomer(custId, custName); // Katagrafi sth MySQL
                            customers.add(new Customer(custId, custName)); // Katagrafi sth runtime arraylist
                            System.out.println("O pelaths apothikeftike!");
                        } else if (cChoice == 2) { // UPDATE CUSTOMER
                            System.out.print("Eisagete ID Pelath: ");
                            String custId = scanner.nextLine();
                            System.out.print("Eisagete to Neo Onoma Pelath: ");
                            String newName = scanner.nextLine();
                            
                            DBManager.updateCustomer(custId, newName); // Enimerwsh sth MySQL
                            for (Customer c : customers) {
                                if (c.getId().equals(custId)) {
                                    customers.remove(c);
                                    customers.add(new Customer(custId, newName));
                                    break;
                                }
                            }
                            System.out.println("Ta stoixeia enimerothikan!");
                        } else if (cChoice == 3) { // DELETE CUSTOMER
                            System.out.print("Eisagete ID Pelath: ");
                            String custId = scanner.nextLine();
                            DBManager.deleteCustomer(custId); // Diagrafh apo MySQL
                            customers.removeIf(c -> c.getId().equals(custId)); // Diagrafh apo dynamic list
                            System.out.println("O pelaths diagrafhke!");
                        } else if (cChoice == 4) { // INTERACTIVE AGORA (Mesw deiktwn pinaka / index selection)
                            if (customers.isEmpty() || packages.isEmpty()) {
                                System.out.println("Sfalma: Prepei na yparxei toylaxiston 1 pelaths kai 1 paketo!");
                                break;
                            }
                            
                            // 1. Ektypwsh dynamic pinaka pelatwn mazi me το index tous gia na epileksei ο xrhsths
                            System.out.println("\n--- Epilegste Pelath ---");
                            for (int i = 0; i < customers.size(); i++) {
                                System.out.println("[" + i + "] ID: " + customers.get(i).getId() + " - Onoma: " + customers.get(i).getName());
                            }
                            System.out.print("Eisagete ton arithmo tou pelath: ");
                            int customerIndex = Integer.parseInt(scanner.nextLine());
                            
                            // 2. Ektypwsh dynamic pinaka paketwn mazi me το index twn antikeimenwn
                            System.out.println("\n--- Epilegste Paketo ---");
                            for (int i = 0; i < packages.size(); i++) {
                                System.out.println("[" + i + "] " + packages.get(i).getName() + " (" + packages.get(i).getPrice() + "$)");
                            }
                            System.out.print("Eisagete ton arithmo tou paketou: ");
                            int packageIndex = Integer.parseInt(scanner.nextLine());
                            
                            // Bounds Check boundaries check gia thn apofuygi IndexOutOfBounds runtime exception
                            if (customerIndex >= 0 && customerIndex < customers.size() && packageIndex >= 0 && packageIndex < packages.size()) {
                                Customer selectedCustomer = customers.get(customerIndex);
                                PackageTour selectedPackage = packages.get(packageIndex);
                                
                                selectedCustomer.buyPackage(selectedPackage); // Diadhtwsh ths sysxetishs sth dynamic RAM
                                System.out.println("\n🎉 O pelaths '" + selectedCustomer.getName() + "' agorase to paketo '" + selectedPackage.getName() + "'!");
                                selectedCustomer.printPackages();
                            } else {
                                System.out.println("Sfalma: Mh egkurh epilogh!");
                            }
                        }
                        break;

                    case 3: // --- DIAXEIRHSH POLHS (CRUD STH MYSQL ME YPOSTHRI3H ID) ---
                        System.out.println("\n--- DIAXEIRHSH POLHS ---");
                        System.out.println("1. Prosthikh Polhs (me ID)\n2. Tropopoihsh kostous polhs\n3. Diagrafh polhs");
                        System.out.print("Epilogh: ");
                        int cityChoice = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Eisagete Onoma Polhs: ");
                        String cityName = scanner.nextLine();

                        if (cityChoice == 1) { // CREATE CITY ME ID
                            System.out.print("Eisagete ID Polhs (Arithmo): ");
                            int cityId = Integer.parseInt(scanner.nextLine());
                            System.out.print("Eisagete Kostos Dianukterefshs: ");
                            double cityCost = Double.parseDouble(scanner.nextLine());
                            
                            DBManager.insertCity(cityId, cityName, cityCost); // SQL Insert query execute
                            cities.add(new City(cityId, cityName, cityCost));
                            System.out.println("H polh apothikeutike me ID: " + cityId);
                        } else if (cityChoice == 2) { // UPDATE CITY
                            System.out.print("Eisagete to Neo Kostos Dianukterefshs: ");
                            double newCost = Double.parseDouble(scanner.nextLine());
                            
                            DBManager.updateCity(cityName, newCost);
                            for (City c : cities) {
                                if (c.getName().equalsIgnoreCase(cityName)) {
                                    int oldId = c.getId();
                                    cities.remove(c);
                                    cities.add(new City(oldId, cityName, newCost));
                                    break;
                                }
                            }
                            System.out.println("To kostos enimerothike!");
                        } else if (cityChoice == 3) { // DELETE CITY
                            DBManager.deleteCity(cityName);
                            cities.removeIf(c -> c.getName().equalsIgnoreCase(cityName));
                            System.out.println("H polh diagrafhke!");
                        }
                        break;

                    case 4: // --- STATISTIKA STOIXEIA ---
                        System.out.println("\n--- STATISTIKA STOIXEIA ---");
                        System.out.println("Sunolikos Arithmos Pelatwn: " + customers.size());
                        System.out.println("Sunolikos Arithmos Touristikwn Paketwn: " + packages.size());
                        break;

                    case 5: // --- APAITHSH: COST & PROFIT ANALYSIS INTERACTIVE ---
                        System.out.println("\n--- OIKONOMIKA STOIXEIA PAKETOU ---");
                        if (packages.isEmpty()) {
                            System.out.println("Den uparxoun kataxwrhmena paketa sto susthma.");
                            break;
                        }
                        
                        // Dynamic loop ektupwshs olwn twn paketwn gia business simulation selection
                        System.out.println("Epilegste to paketo pou thelete na thurwrisete:");
                        for (int i = 0; i < packages.size(); i++) {
                            System.out.println("[" + i + "] " + packages.get(i).getName() + " (ID: " + packages.get(i).getId() + ")");
                        }
                        
                        System.out.print("Eisagete ton arithmo tou paketou: ");
                        int selectedPackIndex = Integer.parseInt(scanner.nextLine());
                        
                        // Business calculation algorithms mesw twn dynamic encapsulation encapsulation methods
                        if (selectedPackIndex >= 0 && selectedPackIndex < packages.size()) {
                            PackageTour targetPackage = packages.get(selectedPackIndex);
                            
                            double cost = targetPackage.getCost(); // Klhsh dynamic algorithmou gia το sunoliko katharo kostos
                            double price = targetPackage.getPrice(); // Klhsh algorithmou gia timh pwlhshs me profit percentage
                            double profit = price - cost; // Business profit equation execution
                            
                            System.out.println("\n========================================");
                            System.out.println("Analysei Stoixeiwn gia to Paketo: " + targetPackage.getName());
                            System.out.println("-> Katharo Kostos (Diamones & Metakiniseis): " + cost + "$"); 
                            System.out.println("-> Katharo Kerdos Praktoreiou (25%): " + profit + "$");    
                            System.out.println("-> Teliki Timi Pwlhshs ston Pelath: " + price + "$");      
                            System.out.println("========================================");
                        } else {
                            System.out.println("Sfalma: Mh egkurh epilogh paketou!");
                        }
                        break;

                    case 6: // --- APOTHIKEFSH (SERIALIZATION DATA PERSISTENCE) & SYSTEM EXIT ---
                        System.out.println("\nTermatismos efarmoghs... Ginetai apothikefsh dedomenwn.");
                        // Klhsh ObjectOutputStream streams gia na sosoume olo το runtime object graph στο arxeio
                        FileManager.saveData(cities, customers, packages);
                        System.out.println("Adios!");
                        break;

                    default:
                        System.out.println("Mh egkurh epilogh!");
                }
            } catch (NumberFormatException e) {
                // Catch handler gia alphanumeric exceptions otan h scanner perimenei int data parsing
                System.out.println("[Sfalma] Parakalw eisagete arithmo.");
            } catch (SQLException e) {
                // Catch handler gia exceptions kata το database connectivity execute status
                System.out.println("[Sfalma SQL] Provlhma sth MySQL: " + e.getMessage());
            } catch (Exception e) {
                // Generic safety fallback handler preventer gia na mhn krasarei pote h konsola
                System.out.println("[Geniko Sfalma] Proekupse sfalma: " + e.getMessage());
            }
        }
        scanner.close(); // Memory optimization leak prevention resource cleaning closure
    }
}
