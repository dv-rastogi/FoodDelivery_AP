package Assign2;

import java.util.ArrayList;
import java.util.HashMap;

class Restaurant { // Normal Restaurant
    private final ResOwner owner;
    private final String name;
    private final HashMap<Integer, foodItem> idToItem; // mapping item id to food item
    private final ArrayList<foodItem> menu;
    private int ordersTaken; // number of orders taken yet, could be an ArrayList of orders in future implementations
    private final String category; // "Authentic" , "FastFood", "Normal"
    protected int discount; // as inherited, protected

    public Restaurant(ResOwner _owner, String _name, ArrayList<foodItem> _menu, String _category) {
        this.owner = _owner;
        this.name = _name;
        this.menu = _menu;
        this.category = _category;
        this.idToItem = new HashMap<>();
        // prepare hash map
        for(foodItem x : this.menu) {
            idToItem.put(x.getId(), x);
        }
        this.ordersTaken = 0;
        this.discount = 0; // default discount
    }

    public HashMap<Integer, foodItem> getIdToItem() {
        return this.idToItem;
    }

    public String getCategory() {
        return this.category;
    }

    public void setDiscount(int _discount) {
        assert !this.name.equals("Normal");
        this.discount = _discount;
    }

    public void addMenu(foodItem newFoodItem) {
        this.menu.add(newFoodItem);
        this.idToItem.put(newFoodItem.getId(), newFoodItem);
    }

    public void displayMenu() {
        System.out.println("Restaurant- " + this.name);
        int idx = 1;
        for(foodItem x : this.menu) {
            System.out.println(idx + ") " + x);
            ++ idx;
        }
    }

    public ResOwner getOwner() {
        return owner;
    }

    public int getOrdersTaken() {
        return this.ordersTaken;
    }

    public void addOrderTaken() {
        this.ordersTaken ++;
    }

    public String getName() {
        return this.name;
    }

    public float afterDiscount(float total) {
        return total;
    }
}

class FastFoodRestaurant extends Restaurant {

    public FastFoodRestaurant(ResOwner _owner, String _name, ArrayList<foodItem> _menu, String _type) {
        super(_owner, _name, _menu, _type);
    }

    @Override
    public float afterDiscount(float total) {
        float off = (float) (((float) this.discount * total) / 100.0);
        return total - off;
    }
}

class AuthenticRestaurant extends Restaurant {

    public AuthenticRestaurant(ResOwner _owner, String _name, ArrayList<foodItem> _menu, String _type) {
        super(_owner, _name, _menu, _type);
    }

    @Override
    public float afterDiscount(float total) {
        float off = (float) (((float) this.discount * total) / 100.0);
        total -= off;
        if(total > 100.0) {
            total -= 50;
        }
        return total;
    }
}

