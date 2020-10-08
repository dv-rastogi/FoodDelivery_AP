package Assign2;

import java.util.ArrayList;
import java.util.Scanner;

class ResOwner implements User {
    private final Restaurant restaurant;
    private final String name;
    private float rewardPoints;
    private final String address;

    public ResOwner(String _name, String _type, String _address, ArrayList<foodItem> _menu, Diners diners) {
        this.name = _name;
        this.address = _address;
        switch (_type) {
            case "Authentic" -> this.restaurant = new AuthenticRestaurant(this, this.name, _menu, _type);
            case "FastFood" -> this.restaurant = new FastFoodRestaurant(this, this.name, _menu, _type);
            default -> this.restaurant = new Restaurant(this, this.name, _menu, _type);
        }
        this.rewardPoints = 0;
        diners.addRestaurant(this.restaurant);
    }

    @Override
    public void login() {
        Scanner sc = new Scanner(System.in);
        int choice;
        boolean exit = false;
        while(!exit) {
            System.out.println("\nWelcome " + this.name + " [" + this.restaurant.getCategory() + "]");
            System.out.println("1) Add item");
            System.out.println("2) Edit item");
            System.out.println("3) Print rewards");
            System.out.println("4) Discount on bill value");
            System.out.println("5) Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Item name: ");
                    sc.nextLine();
                    String itemName = sc.nextLine();
                    System.out.print("Item Price: ");
                    int itemPrice = sc.nextInt();
                    System.out.print("Item Quantity: ");
                    int itemQuantity = sc.nextInt();
                    System.out.print("Item Category: ");
                    String itemCategory = sc.next();
                    System.out.print("Item Discount: ");
                    int itemDiscount = sc.nextInt();
                    foodItem newFoodItem = new foodItem(itemName, itemPrice, itemQuantity, itemCategory, itemDiscount);
                    System.out.println(newFoodItem);
                    this.restaurant.addMenu(newFoodItem);
                }
                case 2 -> {
                    this.restaurant.displayMenu();
                    System.out.print("Enter item ID: ");
                    int chooseItem = sc.nextInt();
                    System.out.println("Choose Attribute: ");
                    System.out.println("1) Name");
                    System.out.println("2) Price");
                    System.out.println("3) Quantity");
                    System.out.println("4) Category");
                    System.out.println("5) Discount");
                    System.out.print("> ");
                    int choiceAttribute = sc.nextInt();
                    foodItem editFoodItem = this.restaurant.getIdToItem().get(chooseItem);
                    switch (choiceAttribute) {
                        case 1 -> {
                            System.out.println("Enter Name: ");
                            sc.nextLine();
                            String editName = sc.nextLine();
                            editFoodItem.setName(editName);
                        }
                        case 2 -> {
                            System.out.print("Enter Price: ");
                            int editPrice = sc.nextInt();
                            editFoodItem.setPrice(editPrice);
                        }
                        case 3 -> {
                            System.out.print("Enter Quantity: ");
                            int editQuantity = sc.nextInt();
                            editFoodItem.setQuantity(editQuantity);
                        }
                        case 4 -> {
                            System.out.print("Enter Category: ");
                            String editCategory = sc.next();
                            editFoodItem.setCategory(editCategory);
                        }
                        case 5 -> {
                            System.out.print("Enter Discount: ");
                            int editDiscount = sc.nextInt();
                            editFoodItem.setDiscount(editDiscount);
                        }
                        default -> System.out.println("INVALID");
                    }
                    System.out.println(editFoodItem);
                }
                case 3 -> System.out.println("Reward Points: " + this.rewardPoints + "/-");
                case 4 -> {
                    if (this.restaurant.getCategory().equals("Normal")) {
                        System.out.println("Not Possible! Change Restaurant Type!");
                        break;
                    }
                    System.out.println("Current Discount: " + this.restaurant.discount + "%");
                    System.out.print("Offer on bill value: ");
                    int newDiscount = sc.nextInt();
                    this.restaurant.setDiscount(newDiscount);
                }
                case 5 -> exit = true;
                default -> System.out.println("INVALID");
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void displayDetails() {
        System.out.println("Restaurant Name: " + this.name);
        System.out.println("Type: " + this.restaurant.getCategory());
        System.out.println("Address: " + this.address);
        System.out.println("Number of orders taken: " + this.restaurant.getOrdersTaken());
        System.out.println("Rating points: " + this.rewardPoints);
    }

    @Override
    public void giveRewardPoints(int points) {
        this.rewardPoints += points;
    }

    @Override
    public String getStatus() {
        return this.restaurant.getCategory();
    }
}