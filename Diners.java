package Assign2;

import java.util.ArrayList;

// Acts as a group of restaurants
class Diners {
    private final ArrayList<Restaurant> restaurants;

    public Diners() {
        this.restaurants = new ArrayList<>();
    }

    public void addRestaurant(Restaurant toAdd) {
        this.restaurants.add(toAdd);
    }

    public ArrayList<Restaurant> getRestaurants() {
        return this.restaurants;
    }

    public void listRestaurants() {
        int idx = 1;
        for(Restaurant x : this.restaurants) {
            System.out.println(idx + ") " + x.getName() + " (" + x.getCategory() + ")");
            ++ idx;
        }
    }
}
