package twentyfour.fall.oop.group1.finalassigment.m24w0517.Service;

import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Bakery;
import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Inventory;
import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Order;

import java.util.List;
import java.util.Scanner;

public class BakeryService {

    private FileHandler fileHandler;

    public BakeryService() {
        fileHandler = new FileHandler();
    }

    public void addItem(Bakery bakery, String name, int quantity, double price, String branchName) {
        List<Inventory> inventory = bakery.getInventory();
        inventory.add(new Inventory(name, quantity, price)); // Add new item to inventory
        fileHandler.saveInventoryToFile(inventory, branchName); // Save inventory to file
    }

    public void displayInventory(Bakery bakery) {
        // Display all items in the bakery's inventory
        for (Inventory item : bakery.getInventory()) {
            System.out.println("Item: " + item.getItemName() + ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
        }
    }




    public void addOrder(Bakery bakery, String customerName, String itemName, int quantity) {
        // Find the item in the inventory
        Inventory item = bakery.getInventory().stream()
                .filter(i -> i.getItemName().equals(itemName))
                .findFirst()
                .orElse(null);

        if (item != null && item.getQuantity() >= quantity) {
            // Calculate the total price
            double totalPrice = item.getPrice() * quantity;
            System.out.println("The total price for " + quantity + " " + itemName + "(s) is: " + totalPrice);

            // Ask for confirmation
            Scanner scanner = new Scanner(System.in);
            System.out.print("Do you want to proceed with the order? (yes/no): ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("yes")) {
                // Reduce the quantity
                item.setQuantity(item.getQuantity() - quantity);

                // Create and save the order
                Order order = new Order(customerName, itemName, quantity);
                fileHandler.saveOrderToFile(order, totalPrice, bakery.getName()); // Save the order to file
                fileHandler.saveInventoryToFile(bakery.getInventory(), bakery.getName()); // Save updated inventory

                System.out.println("Order placed successfully! Total Price: " + totalPrice);
            } else {
                System.out.println("Order cancelled.");
            }
        } else {
            System.out.println("Item not available or insufficient quantity.");
        }
    }

    public void updateItem(Bakery bakery, String itemName, int newQuantity, double newPrice, String branchName) {
        List<Inventory> inventory = bakery.getInventory();

        // Find the item to update
        Inventory itemToUpdate = inventory.stream()
                .filter(i -> i.getItemName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);

        if (itemToUpdate != null) {
            itemToUpdate.setQuantity(newQuantity);
            itemToUpdate.setPrice(newPrice);
            System.out.println("Item updated successfully!");
            fileHandler.saveInventoryToFile(inventory, branchName); // Save updated inventory to file
        } else {
            System.out.println("Item not found in the inventory.");
        }
    }

    public void deleteItem(Bakery bakery, String itemName, String branchName) {
        List<Inventory> inventory = bakery.getInventory();

        // Remove the item from the inventory
        boolean removed = inventory.removeIf(item -> item.getItemName().equalsIgnoreCase(itemName));

        if (removed) {
            System.out.println("Item deleted successfully!");
            fileHandler.saveInventoryToFile(inventory, branchName); // Save updated inventory to file
        } else {
            System.out.println("Item not found in the inventory.");
        }
    }

    public void viewAllInvoices(String branchName) {
        fileHandler.displayAllInvoices(branchName);
    }


}
