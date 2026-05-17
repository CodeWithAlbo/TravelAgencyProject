/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package travelagency;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Xrhsh Scanner gia thn eisodo dedomenwn apo ton xrhsth
        Scanner scanner = new Scanner(System.nanoTime() > 0 ? System.in : null);
        
        // Domes dedomenwn (ArrayLists) gia th diaxeirhsh twn antikeimenwn runtime
        ArrayList<City> cities = new ArrayList<>();
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<PackageTour> packages = new ArrayList<>();

        // 1. Automath fortwsh dedomenwn apo to duadiko arxeio kata thn enarksh
        FileManager.loadData(cities, customers, packages);

        // Stathera pososta kerdous tou agency (p.x. 25%)
        final double agencyProfitMargin = 25.0;

        // 2. Mhxanismos Demo: An oi listes einai kenes, dhmiourgoume arxika dedomena gia epideiksh
        if (cities.isEmpty() && packages.isEmpty() && customers.isEmpty()) {
            System.out.println("\n[Demo] Dhmiourgia arxikwn antikeimenwn gia thn epideiksh ths efarmoghs...");
            
            // Dhmiourgia polewn
            City athens = new City("Athens", 0.0); // Polh anaxwrishs (0 kostos dianikterefshs)
            City rome = new City("Rome", 80.0);
            City paris = new City("Paris", 120.0);
            
            cities.add(athens);
            cities.add(rome);
            cities.add(paris);

            // Dhmiourgia paketou (sinthesh)
            PackageTour EuropeTour = new PackageTour(101, "Europe Panorama", agencyProfitMargin);
            EuropeTour.addCity(athens, 0); // Anaxwrhsh
            EuropeTour.addCity(rome, 3);   // 3 nuxtes sth rwmh
            EuropeTour.addCity(paris, 4);  // 4 nuxtes sto parisi
            EuropeTour.addCity(athens, 0); // Epistrofh

            // Prosthikh polimorfikwn metakinhsewn (Polumorfismos)
            Travel flight = new AirFlight(athens, rome, 150.0, "Aegean Airlines");
            Travel train = new Train(rome, paris, 90.0);
            Travel returnFlight = new AirFlight(paris, athens, 180.0, "Air France");
            
            EuropeTour.addTravel(flight);
            EuropeTour.addTravel(train);
            EuropeTour.addTravel(returnFlight);
            
            packages.add(EuropeTour);

            // Dhmiourgia pelath
            Customer guest = new Customer("CUST-001", "Elton Paptsi");
            // O pelaths agorazei to paketo
            guest.buyPackage(EuropeTour);
            customers.add(guest);
            
            System.out.println("[Demo] Ta arxika dedomena dhmiourgithikan epitixws!\n");
        }

        // 3. Vasiko menu leitourgiwn
        int choice = 0;
        while (choice != 6) {
            System.out.println("\n================= VASIKO MENU =================");
            System.out.println("1) Diaxeirhsh Paketou (Prosthikh / Emfanish)");
            System.out.println("2) Diaxeirhsh Pelath (Prosthikh / Tropopoihsh / Diagrafh / Agora)");
            System.out.println("3) Diaxeirhsh Polhs (Prosthikh / Tropopoihsh / Diagrafh)");
            System.out.println("4) Emfanhsh plithous Pelatwn kai Paketwn");
            System.out.println("5) Ypologismos & Emfanisi sunolikou kerdous");
            System.out.println("6) Apothikefsh dedomenwn & Eksodos apo thn efarmogh");
            System.out.print("Parakalw Epilekste (1-6): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1: // Diaxeirhsh Paketou
                        System.out.println("\n--- DIAXEIRHSH PAKETOU ---");
                        System.out.println("1. Prosthikh Neou Paketou\n2. Provolh olwn twn paketwn (Epideiksh)");
                        System.out.print("Epilogh: ");
                        int pChoice = Integer.parseInt(scanner.nextLine());
                        
                        if (pChoice == 1) {
                            System.out.print("Eisagete Kwdiko (ID) Paketou: ");
                            int id = Integer.parseInt(scanner.nextLine());
                            System.out.print("Eisagete Onoma Paketou: ");
                            String name = scanner.nextLine();
                            
                            PackageTour p = new PackageTour(id, name, agencyProfitMargin);
                            // Sinthesh me tis uparxouses poleis gia to demo
                            if (cities.size() >= 2) {
                                p.addCity(cities.get(0), 0);
                                p.addCity(cities.get(1), 3);
                                p.addCity(cities.get(0), 0);
                                System.out.println("[Sinthesh] Sto paketo prostethike automata h diadromh: " + cities.get(0).getName() + " -> " + cities.get(1).getName());
                            }
                            packages.add(p);
                            System.out.println("To touristiko paketo prostethike epitixws!");
                        } else if (pChoice == 2) {
                            if (packages.isEmpty()) {
                                System.out.println("Den uparxoun kataxwrhmena paketa.");
                            } else {
                                for (PackageTour p : packages) {
                                    p.printPackage(); // Klhsh ektipwshs paketou
                                }
                            }
                        }
                        break;

                    case 2: // Diaxeirhsh Pelath (CRUD & Agora)
                        System.out.println("\n--- DIAXEIRHSH PELATH ---");
                        System.out.println("1. Prosthikh Pelath (Create)\n2. Tropopoihsh Pelath (Update)\n3. Diagrafh Pelath (Delete)\n4. Kataxwrhsh agoras paketou & Ektupwsh");
                        System.out.print("Epilogh: ");
                        int cChoice = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Eisagete ID Pelath: ");
                        String custId = scanner.nextLine();

                        if (cChoice == 1) {
                            System.out.print("Eisagete Onoma Pelath: ");
                            String custName = scanner.nextLine();
                            
                            // Ektelesh CRUD sth MySQL kai prosthikh sth runtime lista
                            DBManager.insertCustomer(custId, custName);
                            customers.add(new Customer(custId, custName));
                            System.out.println("O pelaths apothikeftike epituxws sth vash dedomenwn kai sto susthma!");
                        } else if (cChoice == 2) {
                            System.out.print("Eisagete to Neo Onoma Pelath: ");
                            String newName = scanner.nextLine();
                            
                            DBManager.updateCustomer(custId, newName);
                            // Enhmerwsh kai sth runtime lista
                            for (Customer c : customers) {
                                if (c.getId().equals(custId)) {
                                    // Shmeiwsh: Logw elleipshs setter stis shmeiwseis, antikathistate to runtime antikeimeno gia asfaleia
                                    customers.remove(c);
                                    customers.add(new Customer(custId, newName));
                                    break;
                                }
                            }
                            System.out.println("Ta stoixeia tou pelath enhmerwthikan sth Vash Dedomenwn!");
                        } else if (cChoice == 3) {
                            DBManager.deleteCustomer(custId);
                            customers.removeIf(c -> c.getId().equals(custId));
                            System.out.println("O pelaths diagrafhke epituxws apo th Vash Dedomenwn!");
                        } else if (cChoice == 4) {
                            // Agora Paketou
                            Customer currentCust = null;
                            for (Customer c : customers) {
                                if (c.getId().equals(custId)) { currentCust = c; break; }
                            }
                            
                            if (currentCust == null) {
                                System.out.println("O pelaths de vrethike. Prosthikh neou pelath automata.");
                                System.out.print("Eisagete Onoma gia Neo Pelath: ");
                                String cName = scanner.nextLine();
                                currentCust = new Customer(custId, cName);
                                DBManager.insertCustomer(custId, cName);
                                customers.add(currentCust);
                            }

                            if (packages.isEmpty()) {
                                System.out.println("Den uparxei diathesimo paketo gia agora!");
                            } else {
                                currentCust.buyPackage(packages.get(0));
                                System.out.println("H agora tou paketou '" + packages.get(0).getName() + "' olokrirothike!");
                                currentCust.printPackages();
                            }
                        }
                        break;

                    case 3: // Diaxeirhsh polhs (CRUD)
                        System.out.println("\n--- DIAXEIRHSH POLHS ---");
                        System.out.println("1. Prosthikh Polhs (Create)\n2. Tropopoihsh kostous polhs (Update)\n3. Diagrafh polhs (Delete)");
                        System.out.print("Epilogh: ");
                        int cityChoice = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Eisagete Onoma Polhs: ");
                        String cityName = scanner.nextLine();

                        if (cityChoice == 1) {
                            System.out.print("Eisagete Kostos Dianukterefshs: ");
                            double cityCost = Double.parseDouble(scanner.nextLine());
                            
                            DBManager.insertCity(cityName, cityCost);
                            cities.add(new City(cityName, cityCost));
                            System.out.println("H polh apothikeutike epituxws sth Vash Dedomenwn!");
                        } else if (cityChoice == 2) {
                            System.out.print("Eisagete to Neo Kostos Dianukterefshs: ");
                            double newCost = Double.parseDouble(scanner.nextLine());
                            
                            DBManager.updateCity(cityName, newCost);
                            cities.removeIf(c -> c.getName().equalsIgnoreCase(cityName));
                            cities.add(new City(cityName, newCost));
                            System.out.println("To kostos ths polhs enimerothike sth Vash Dedomenwn!");
                        } else if (cityChoice == 3) {
                            DBManager.deleteCity(cityName);
                            cities.removeIf(c -> c.getName().equalsIgnoreCase(cityName));
                            System.out.println("H polh diagrafhke epituxws apo th Vash Dedomenwn!");
                        }
                        break;

                    case 4: // Emfanisi plithous pelatwn + paketwn
                        System.out.println("\n--- STATISTIKA STOIXEIA PRAKTOREIOU ---");
                        System.out.println("Sunolikos Arithmos Pelatwn: " + customers.size());
                        System.out.println("Sunolikos Arithmos Touristikwn Paketwn: " + packages.size());
                        break;

                    case 5: // Ypologismos sunolikou kerdous apo tis pwlhseis
                        System.out.println("\n--- OIKONOMIKA STOIXEIA ---");
                        double totalAgencyProfit = 0;
                        for (Customer c : customers) {
                            for (PackageTour p : c.getPurchasedPackages()) {
                                // To kerdos pou prokuptei apo th diafora timhs pwlhshs kai kostous
                                totalAgencyProfit += (p.getPrice() - p.getCost());
                            }
                        }
                        System.out.println("Sunoliko Kerdos Praktoreiou apo poulhmena paketa: " + totalAgencyProfit + "€");
                        break;

                    case 6: // Apothikefsh kai Eksodos
                        System.out.println("\nTermatismos efarmoghs... Ginetai apothikefsh dedomenwn.");
                        FileManager.saveData(cities, customers, packages);
                        System.out.println("Adios!");
                        break;

                    default:
                        System.out.println("Mh egkurh epilogh! Parakalw eisagete arithmo apo 1 ews 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[Sfalma Eksaireshs] Mh egkurh morfh dedomenou! Parakalw eisagete arithmo. (Exception Handled)");
            } catch (SQLException e) {
                System.out.println("[Sfalma SQL] Provlhma kata thn epikoinwnia MySQL: " + e.getMessage() + " (Exception Handled)");
            } catch (Exception e) {
                System.out.println("[Geniko Sfalma] Proekupse aprovlepto sfalma: " + e.getMessage() + " (Exception Handled)");
            }
        }
        scanner.close();
    }
}