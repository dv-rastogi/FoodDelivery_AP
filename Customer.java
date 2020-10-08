package Assign2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Customer implements User {
    private final String name;
    private final String type;
    private final String address;
    private float balance;
    private float rewardPoints;
    private int deliveryCharge;
    private float appRevenue;
    private Cart cart;
    protected final Diners diners; // as inherited protected
    private final App driverApp;
    private final Queue<Order> orders;

    public Customer(String _name, String _type, String _address, Diners _diners, App _driverApp) {
        this.name = _name;
        this.type = _type;
        this.address = _address;
        this.diners = _diners;
        this.balance = 1000;
        this.rewardPoints = 0;
        this.cart = null;
        this.deliveryCharge = 40;
        this.appRevenue = 0;
        this.driverApp = _driverApp;
        this.orders = new LinkedList<>();
    }

    @Override
    public void login() {
        Scanner sc = new Scanner(System.in);
        int choice;
        boolean exit = false;
        while(!exit) {
            System.out.println("\nWelcome " + this.name + " [" + this.type + "]");
            System.out.println("1) Place Order / Add item");
            System.out.println("2) Checkout");
            System.out.println("3) Reward won");
            System.out.println("4) Recent Orders");
            System.out.println("5) Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    // place order
                    System.out.println("Choose Restaurant: ");
                    this.diners.listRestaurants();
                    System.out.print("Enter choice: ");
                    int choiceRes = sc.nextInt();
                    if(this.isCartOccupied()) {
                        // Error Message if different restaurant chosen
                        Restaurant restaurantChosen = this.diners.getRestaurants().get(choiceRes - 1);
                        if(restaurantChosen != this.cart.getRestaurant()) {
                            System.out.println("Cart contains items from another restaurant! Checkout or Clear cart!");
                            System.out.print("Clear Cart ? [1 / 0]: ");
                            boolean clearCart = sc.nextInt() == 1;
                            if(clearCart) {
                                this.cart = null;
                                System.out.println("Cart Cleared!");
                            }
                            break;
                        }
                    }
                    else {
                        // create a cart
                        this.cart = new Cart(this, this.diners.getRestaurants().get(choiceRes - 1));
                    }

                    assert this.isCartOccupied();
                    // add an item
                    this.cart.getRestaurant().displayMenu();
                    System.out.print("Enter item ID: ");
                    int itemID = sc.nextInt();
                    System.out.print("Enter item quantity: ");
                    int itemQuantity = sc.nextInt();

                    // Can't order more than quantity
                    foodItem itemChosen = this.cart.getRestaurant().getIdToItem().get(itemID);
                    if(itemQuantity > itemChosen.getQuantity()) {
                        System.out.println("Order can't be placed! Invalid quantity!");
                        if(this.cart.empty()) {
                            this.cart = null;
                        }
                    }
                    else if(this.cart.alreadyChosen(itemID)) {
                        System.out.println("Item already chosen!");
                    }
                    else {
                        foodItem addNewItem = new foodItem(itemChosen, itemQuantity);
                        this.cart.addItem(addNewItem);
                        System.out.println("Item added!");
                    }
                }
                case 2 -> {
                    // checkout
                    if(this.isCartOccupied()) {
                        boolean success = this.cart.checkout(this.driverApp);
                        if(success) {
                            this.addAdjust(new Order(this.cart));
                            System.out.println("Order Placed!");
                            System.out.println("Successfully bought for " + (this.cart.getTotal() + this.cart.getDeliveryCharge()) + "/-");
                        }
                        else {
                            System.out.println("Order Failed!");
                        }
                        this.cart = null;
                    }
                    else {
                        // Error Message
                        System.out.println("Cart empty!");
                    }
                }
                case 3 -> System.out.println("Rewards: " + this.rewardPoints + "/-");
                case 4 -> {
                    System.out.println("Past Orders for " + this.name);
                    int orderIdx = 1;
                    for(Order x : orders){
                        System.out.println(orderIdx + "> " + x);
                        ++ orderIdx;
                    }
                }
                case 5 -> exit = true;
                default -> System.out.println("INVALID");
            }
        }
    }

    // maintain the queue
    private void addAdjust(Order _order) {
        this.orders.add(_order);
        int ordersLim = 10;
        if(this.orders.size() > ordersLim) {
            this.orders.poll();
        }
        assert this.orders.size() <= ordersLim;
    }

    // calculate reward according to restaurants
    public int calcReward(float total, String RestaurantType) {
        int reward;
        switch (RestaurantType) {
            case "FastFood" -> reward = 10 * ((int) total / 150);
            case "Authentic" -> reward = 25 * ((int) total / 200);
            default -> reward = 5 * ((int) total / 100);
        }
        return reward;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void displayDetails() {
        System.out.println("Name: " + this.name);
        System.out.println("Type: " + this.type);
        System.out.println("Address: " + this.address);
        System.out.println("Account Balance: " + this.balance);
        System.out.println("Reward Points: " + this.rewardPoints);
    }

    @Override
    public void giveRewardPoints(int points) {
        this.rewardPoints += points;
    }

    @Override
    public String getStatus() {
        return this.type;
    }

    public boolean isCartOccupied() {
        return this.cart != null;
    }

    public float afterDiscount(float total) {
        return total;
    }

    public float getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public float getRewardPoints() {
        return this.rewardPoints;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setRewardPoints(float _rewardPoints) {
        this.rewardPoints = _rewardPoints;
    }

    public void setBalance(float _balance) {
        this.balance = _balance;
    }

    public void addAppRevenue(float generatedAppRevenue) {
        this.appRevenue += generatedAppRevenue;
    }

    protected void setDeliveryCharge(int _deliveryCharge) {
        this.deliveryCharge = _deliveryCharge;
    }

    public float getAppRevenue() {
        return appRevenue;
    }
}

class SpecialCustomer extends Customer {

    public SpecialCustomer(String _name, String _type, String _address, Diners _diners, App _app) {
        super(_name, _type, _address, _diners, _app);
        this.setDeliveryCharge(20);
    }

    @Override
    public float afterDiscount(float total) {
        if(total > 200){
            total -= 25;
        }
        return total;
    }
}

class EliteCustomer extends Customer {

    public EliteCustomer(String _name, String _type, String _address, Diners _diners, App _app) {
        super(_name, _type, _address, _diners, _app);
        this.setDeliveryCharge(0);
    }

    @Override
    public float afterDiscount(float total) {
        if(total > 200){
            total -= 50;
        }
        return total;
    }
}

