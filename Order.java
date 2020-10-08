package Assign2;

import java.util.ArrayList;

// Keep track of what has been ordered
class Order {
    private final String restaurant;
    private final String restaurantCategory;
    private final float deliveryCharge;
    private final float totalBill;
    private final ArrayList<foodItem> bought;

    Order(Cart cart) {
        this.restaurant = cart.getRestaurant().getName();
        this.restaurantCategory = cart.getRestaurant().getCategory();
        this.deliveryCharge = cart.getDeliveryCharge();
        this.totalBill = cart.getTotal() + this.deliveryCharge;
        this.bought = cart.getOrdered();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Restaurant: " + this.restaurant +  " (" + this.restaurantCategory + ")" + "\n");
        int idx = 1;
        for(foodItem x : this.bought) {
            res.append(idx).append(") Bought: ").append(x.getName()).append(" ; Quantity: ").append(x.getQuantity()).append(" for ").append(x.getPrice()).append("/- each\n");
            ++ idx;
        }
        res.append("Bill total: ").append(this.totalBill).append("/- , Delivery Charge: ").append(this.deliveryCharge).append("/-");
        return res.toString();
    }
}