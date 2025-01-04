package twentyfour.fall.oop.group1.finalassigment.m24w0517.model;

public class Inventory {
    private String itemName;
    private int quantity;
    private double price;

    public Inventory(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and setters
    public String getItemName() {
        return itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Optional method to increase stock
    public void increaseQuantity(int additionalQuantity) {
        this.quantity += additionalQuantity;
    }

    public void setPrice(double price) {
        this.price = price; // Correctly updates the price field
    }


}
