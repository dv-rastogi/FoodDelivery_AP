package Assign2;

class foodItem {
    private static int globalID = 0;
    private final int id;
    private String name;
    private int price;
    private int discount;
    private int quantity;
    private String category;

    public foodItem(String _name, int _price, int _quantity, String _category, int _discount) {
        this.id = ++ globalID;
        this.name = _name;
        this.price = _price;
        this.quantity = _quantity;
        this.category = _category;
        this.discount = _discount;
    }

    public foodItem(int _id, String _name, int _price, int _quantity, String _category, int _discount) {
        this.id = _id;
        this.name = _name;
        this.price = _price;
        this.quantity = _quantity;
        this.category = _category;
        this.discount = _discount;
    }

    // Copy Constructor
    public foodItem(foodItem that, int _quantity) {
        this(that.id, that.name, that.price, _quantity, that.category, that.discount);
    }

    public int getId() {
        return this.id;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setPrice(int _price) {
        this.price = _price;
    }

    public void setQuantity(int _quantity) {
        this.quantity = _quantity;
    }

    public void setCategory(String _category) {
        this.category = _category;
    }

    public void setDiscount(int _discount) {
        this.discount = _discount;
    }

    // price after discount
    public float getNetPrice() {
        float off = (float) ((this.discount * (float) this.price) / 100.0);
        return this.quantity * ((float) this.price - off);
    }

    public void decreaseQuantity(int itemQuantity) {
        this.quantity -= itemQuantity;
        assert this.quantity >= 0;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return ("ID " + this.id + " >> " + this.name + " for " + this.price + "/- each; " + this.quantity + " units with " + this.discount + "% off" + " ; [" + this.category + "]");
    }
}
