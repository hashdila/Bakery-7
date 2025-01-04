package twentyfour.fall.oop.group1.finalassigment.m24w0517.model;

import java.util.ArrayList;
import java.util.List;

public class Bakery {
    private String name;
    private List<Inventory> inventory; // This will store the bakery's inventory

    // Constructor to initialize bakery name and empty inventory list
    public Bakery(String name) {
        this.name = name;
        this.inventory = new ArrayList<>(); // Initialize the inventory as an empty list
    }

    // Getter for bakery name
    public String getName() {
        return name;
    }

    // Getter for inventory list
    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }

    // You can also add more methods to manipulate the inventory list if necessary
}
