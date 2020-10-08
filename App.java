package Assign2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class App {
    private final String name;
    private final Diners diners;
    private final ArrayList<User> resOwners;
    private final ArrayList<User> customers;
    private float revenue;
    private int deliveryCharges;

    App(String _name) {
        this.name = _name;
        this.diners = new Diners();
        this.resOwners = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.revenue = 0;
        this.deliveryCharges = 0;
        this.getData();
        this.runApp();
    }

    private void getData() {
        System.out.println("Database formed ....\n");
        File f1, f2;
        f1 = new File("Data.txt"); //for non intellij workspace
        f2 = new File("src\\Assign2\\Data.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(f1);
        } catch (FileNotFoundException e1){
            try {
                sc = new Scanner(f2);
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
        }
        assert sc != null;

        // fill restaurants
        int nr = sc.nextInt();
        sc.nextLine();
        System.out.println("Number of Restaurants: " + nr + "\n");
        for(int i = 0; i < nr; ++ i){
            // create a new restaurant owner here and pass Diners [Dependency]
            String name = sc.nextLine();
            System.out.println("Name- " + name);
            String type = sc.next();
            sc.nextLine();
            System.out.println("Type- " + type);
            String address = sc.nextLine();
            System.out.println("Address- " + address);
            int nItems = sc.nextInt();
            sc.nextLine();
            ArrayList<foodItem> newMenu = new ArrayList<>();
            for(int j = 0; j < nItems; ++ j) {
                String itemName = sc.nextLine();
                int itemPrice = sc.nextInt();
                int itemQuantity = sc.nextInt();
                sc.nextLine();
                String itemCategory = sc.next();
                int itemDiscount = sc.nextInt();
                foodItem newFoodItem = new foodItem(itemName, itemPrice, itemQuantity, itemCategory, itemDiscount);
                System.out.println(newFoodItem);
                newMenu.add(newFoodItem);
                sc.nextLine();
            }
            User newUser = new ResOwner(name, type, address, newMenu, this.diners);
            this.resOwners.add(newUser);
            System.out.println();
        }

        // fill customers
        int nc = sc.nextInt();
        sc.nextLine();
        System.out.println("Number of Customers: " + nc + "\n");
        for(int i = 0; i < nc; ++ i) {
            // create a new Customer here, and associate it with Diners
            String name = sc.nextLine();
            System.out.println("Name- " + name);
            String type = sc.next();
            sc.nextLine();
            System.out.println("Type- " + type);
            String address = sc.nextLine();
            System.out.println("Address- " + address);

            User newUser;
            switch (type){
                case "Special" -> newUser = new SpecialCustomer(name, type, address, this.diners, this);
                case "Elite" -> newUser = new EliteCustomer(name, type, address, this.diners, this);
                default -> newUser = new Customer(name, type, address, this.diners, this);
            }
            this.customers.add(newUser);
            System.out.println();
        }

        System.out.print("============\n");
    }

    private void runApp() {
        Scanner sc = new Scanner(System.in);
        int choice;
        boolean exit = false;
        while(!exit) {
            System.out.println("\nWelcome to " + this.name + "!");
            System.out.println("1) Enter as Restaurant Owner");
            System.out.println("2) Enter as Customer");
            System.out.println("3) Check User Details");
            System.out.println("4) Check Revenue");
            System.out.println("5) Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    // Restaurant Owner Login
                    System.out.println("Choose Restaurant: ");
                    int idx1 = 1;
                    for (User x : this.resOwners) {
                        System.out.println(idx1 + ". " + x.getName() + " (" + x.getStatus() + ")");
                        ++idx1;
                    }
                    System.out.print("Enter choice: ");
                    int chooseOwner = sc.nextInt();
                    this.resOwners.get(chooseOwner - 1).login();
                }
                case 2 -> {
                    // Customer Login
                    System.out.println("Choose Customer: ");
                    int idx2 = 1;
                    for (User x : this.customers) {
                        System.out.println(idx2 + ". " + x.getName() + " (" + x.getStatus() + ")");
                        ++idx2;
                    }
                    System.out.print("Enter choice: ");
                    int chooseCustomer = sc.nextInt();
                    this.customers.get(chooseCustomer - 1).login();
                }
                case 3 -> {
                    // Display user details
                    System.out.print("1) Restaurant 2) Customer: ");
                    int displayChoice = sc.nextInt();
                    System.out.println("Enter name to search: ");
                    sc.nextLine();
                    String displayName = sc.nextLine();
                    switch (displayChoice) {
                        case 1 -> {
                            // look in res owners
                            boolean foundResOwner = false;
                            for (User x : this.resOwners) {
                                if (x.getName().equals(displayName)) {
                                    foundResOwner = true;
                                    x.displayDetails();
                                    break;
                                }
                            }
                            if (!foundResOwner) {
//                                System.out.println("Search Name- " + displayName);
                                System.out.println("NOT FOUND");
                            }
                        }
                        case 2 -> {
                            // look in customers
                            boolean foundCustomer = false;
                            for (User x : this.customers) {
                                if (x.getName().equals(displayName)) {
                                    foundCustomer = true;
                                    x.displayDetails();
                                    break;
                                }
                            }
                            if (!foundCustomer) {
//                                System.out.println("Search Name- " + displayName);
                                System.out.println("NOT FOUND");
                            }
                        }
                        default -> System.out.println("INVALID");
                    }
                }
                case 4 -> {
                    // Tell revenue
                    System.out.println("Revenue Generated: " + this.revenue + "/-");
                    System.out.println("Total Delivery Charges Collected: " + this.deliveryCharges + "/-");
                }
                case 5 -> exit = true;
                default -> System.out.println("INVALID");
            }
        }
    }
    public void reward(User _user, int points) {
        _user.giveRewardPoints(points);
    }
    public void finaliseOrder(float appRevenue, int appDeliveryCharges) {
        this.revenue += appRevenue;
        this.deliveryCharges += appDeliveryCharges;
    }
}

interface User {
    // login, status, display details, login, give reward points
    String getName();
    String getStatus();
    void displayDetails();
    void login();
    void giveRewardPoints(int points);
}