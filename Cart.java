package Assign2;

import java.util.ArrayList;
import java.util.HashMap;

class Cart {
    private final Restaurant restaurant;
    private final Customer customer;
    private final ArrayList<foodItem> toOrder;
    private final HashMap<Integer, Boolean> isChosen;
    private float total;
    private float deliveryCharge;

    public Cart(Customer _customer, Restaurant _restaurant) {
        this.customer = _customer;
        this.restaurant = _restaurant;
        toOrder = new ArrayList<>();
        isChosen = new HashMap<>();
        this.total = 0;
        this.deliveryCharge = 0;
    }

    public void addItem(foodItem newItem) {
        this.toOrder.add(newItem);
        this.isChosen.put(newItem.getId(), true);
        this.total += newItem.getNetPrice();
    }

    public void formTotal() {
        // total is discounted price over food items
        System.out.println("Total after food item discounts: " + this.total + "/-");
        this.total = this.restaurant.afterDiscount(this.total);
        // discount over restaurant
        System.out.println("Total after restaurant discounts: " + this.total + "/-");
        // discount over customers
        this.total = this.customer.afterDiscount(this.total);
        System.out.println("Total after customer discounts: " + this.total + "/-");
    }

    // return whether checkout is a success
    public boolean checkout(App driverApp) {
        assert this.deliveryCharge == 0;
        // will calculate total bill of items
        this.printCart();
        this.formTotal();
        // generate revenue
        float generatedAppRevenue = (float) (this.total / 100.0);
        // add delivery charges
        this.deliveryCharge = this.customer.getDeliveryCharge();
        float finalBill = this.total + this.deliveryCharge;

        System.out.println("> Final Bill value: " + this.total + "/-");
        System.out.println("> Delivery Charge: " + this.deliveryCharge + "/-");
        System.out.println("> Final Total (Final Bill Value + Delivery Charge): " + finalBill + "/-");

        // check if possible
        if(finalBill > this.customer.getRewardPoints() + this.customer.getBalance()) {
            System.out.println("Order can't be placed! Insufficient balance! Clearing cart...");
            return false;
        }
        // subtract from reward points first and then from balance
        float temp = this.customer.getRewardPoints();
        this.customer.setRewardPoints(this.customer.getRewardPoints() - Math.min(this.customer.getRewardPoints(), finalBill));
        finalBill -= Math.min(finalBill,temp);
        temp = this.customer.getBalance();
        this.customer.setBalance(this.customer.getBalance() - Math.min(this.customer.getBalance(), finalBill));
        finalBill -= Math.min(finalBill, temp);
        assert finalBill == 0;

        // On Success
        // add app revenue
        this.customer.addAppRevenue(generatedAppRevenue);
        // decrease quantities & add the order taken
        this.restaurant.addOrderTaken();
        for(foodItem x : this.toOrder) {
            this.restaurant.getIdToItem().get(x.getId()).decreaseQuantity(x.getQuantity());
        }
        // will reward points & add revenue to app
        int givePoints = this.customer.calcReward(this.total, this.restaurant.getCategory());
        driverApp.reward(this.customer, givePoints);
        driverApp.reward(this.restaurant.getOwner(), givePoints);
        driverApp.finaliseOrder(this.customer.getAppRevenue(), (int) this.deliveryCharge);
        return true;
    }

    public void printCart() {
        System.out.println("Your cart: ");
        System.out.println("Restaurant: " + this.restaurant.getName() + " (" + this.restaurant.getCategory() + ")");
        int idx = 1;
        for(foodItem x : this.toOrder) {
            System.out.println(idx + ") " + x);
            ++ idx;
        }
    }
    
    public boolean alreadyChosen(int itemID) {
        // see if an item is already present in cart
        return this.isChosen.containsKey(itemID);
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public boolean empty() {
        return this.toOrder.isEmpty();
    }

    public float getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public float getTotal() {
        return total;
    }

    public ArrayList<foodItem> getOrdered() {
        return this.toOrder;
    }
}