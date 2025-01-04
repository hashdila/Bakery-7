package twentyfour.fall.oop.group1.finalassigment.m24w0517;

import twentyfour.fall.oop.group1.finalassigment.m24w0517.Service.BakeryService;
import twentyfour.fall.oop.group1.finalassigment.m24w0517.Service.FileHandler;
import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Bakery;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final String MAIN_ADMIN_PASSWORD = "adminmain";
    private static final Map<String, String> BAKERY_ADMIN_PASSWORDS = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BakeryService bakeryService = new BakeryService();
        FileHandler fileHandler = new FileHandler();



        // Initialize bakeries and load inventory from files
        Bakery kandyBakery = new Bakery("Kandy Bakery");
        kandyBakery.setInventory(fileHandler.loadInventoryFromFile("Kandy Bakery"));

        Bakery colomboBakery = new Bakery("Colombo Bakery");
        colomboBakery.setInventory(fileHandler.loadInventoryFromFile("Colombo Bakery"));

        Bakery galleBakery = new Bakery("Galle Bakery");
        galleBakery.setInventory(fileHandler.loadInventoryFromFile("Galle Bakery"));


        // Initialize bakery-specific admin passwords
        BAKERY_ADMIN_PASSWORDS.put("Kandy Bakery", "admink");
        BAKERY_ADMIN_PASSWORDS.put("Colombo Bakery", "adminc");
        BAKERY_ADMIN_PASSWORDS.put("Galle Bakery", "adming");

        while (true) {
            System.out.println("\n=== Welcome to Bakery Management System ===");
            System.out.println("1. Kandy Bakery");
            System.out.println("2. Colombo Bakery");
            System.out.println("3. Galle Bakery");
            System.out.println("4. Main Admin Login");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    branchMenu(scanner, bakeryService, kandyBakery, "Kandy Bakery");
                    break;
                case 2:
                    branchMenu(scanner, bakeryService, colomboBakery, "Colombo Bakery");
                    break;
                case 3:
                    branchMenu(scanner, bakeryService, galleBakery, "Galle Bakery");
                    break;
                case 4:
                    if (mainAdminLogin(scanner)) {
                        mainAdminMenu(scanner, bakeryService, kandyBakery, colomboBakery, galleBakery);
                    } else {
                        System.out.println("Invalid password! Returning to main menu.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static boolean mainAdminLogin(Scanner scanner) {
        System.out.print("Enter Main Admin Password: ");
        String inputPassword = scanner.next();
        return MAIN_ADMIN_PASSWORD.equals(inputPassword);
    }



    private static void mainAdminMenu(Scanner scanner, BakeryService bakeryService, Bakery kandyBakery, Bakery colomboBakery, Bakery galleBakery) {
        while (true) {
            System.out.println("\n=== Main Admin Menu ===");
            System.out.println("1. View All Branch Inventories");
            System.out.println("2. View all brances invoices");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Kandy Bakery Inventory ---");
                    bakeryService.displayInventory(kandyBakery);
                    System.out.println("\n--- Colombo Bakery Inventory ---");
                    bakeryService.displayInventory(colomboBakery);
                    System.out.println("\n--- Galle Bakery Inventory ---");
                    bakeryService.displayInventory(galleBakery);
                    break;
                case 2:
//
                    System.out.println("\n--- Kandy Bakery Invoices ---");
                    bakeryService.viewAllInvoices("Kandy Bakery");
                    System.out.println("\n--- Colombo Bakery Invoices ---");
                    bakeryService.viewAllInvoices("Colombo Bakery");
                    System.out.println("\n--- Galle Bakery Invoices ---");
                    bakeryService.viewAllInvoices("Galle Bakery");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    private static void branchMenu(Scanner scanner, BakeryService bakeryService, Bakery bakery, String branchName) {
        while (true) {
            System.out.println("\n=== Welcome to " + branchName + " Bakery ===");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer");
            System.out.println("3. Return to Main Menu");
            System.out.print("Choose an option: ");
            int userTypeChoice = scanner.nextInt();

            switch (userTypeChoice) {
                case 1:
                    if (adminLogin(scanner, branchName)) {
                        adminMenu(scanner, bakeryService, bakery, branchName); // Pass branchName here
                    } else {
                        System.out.println("Invalid password! Returning to branch menu.");
                    }
                    break;
                case 2:
                    customerMenu(scanner, bakeryService, bakery);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }


    private static boolean adminLogin(Scanner scanner, String branchName) {
        System.out.print("Enter Admin Password for " + branchName + " Bakery: ");
        String inputPassword = scanner.next();
        return BAKERY_ADMIN_PASSWORDS.get(branchName).equals(inputPassword);
    }

    private static void adminMenu(Scanner scanner, BakeryService bakeryService, Bakery bakery, String branchName) {
        while (true) {
            System.out.println("\n=== Admin Menu for " + bakery.getName() + " ===");
            System.out.println("1. Add Item to Inventory");
            System.out.println("2. Update Item in Inventory");
            System.out.println("3. Delete Item from Inventory");
            System.out.println("4. Display Inventory");
            System.out.println("5. Return to Branch Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    bakeryService.addItem(bakery, name, quantity, price, branchName);
                    break;
                case 2:
                    bakeryService.displayInventory(bakery);
                    System.out.print("Enter item name to update: ");
                    String updateName = scanner.next();
                    System.out.print("Enter new quantity: ");
                    int newQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    bakeryService.updateItem(bakery, updateName, newQuantity, newPrice, branchName);
                    break;
                case 3:
                    bakeryService.displayInventory(bakery);
                    System.out.print("Enter item name to delete: ");
                    String deleteName = scanner.next();
                    bakeryService.deleteItem(bakery, deleteName, branchName);
                    break;
                case 4:
                    bakeryService.displayInventory(bakery);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }



    private static void customerMenu(Scanner scanner, BakeryService bakeryService, Bakery bakery) {
        while (true) {
            System.out.println("\n=== Customer Menu for " + bakery.getName() + " ===");
            System.out.println("1. Place an Order");
            System.out.println("2. Display Available Items");
            System.out.println("3. Return to Branch Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bakeryService.displayInventory(bakery);
                    System.out.print("Enter your name: ");
                    String customerName = scanner.next();
                    System.out.print("Enter item name: ");
                    String itemName = scanner.next();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    bakeryService.addOrder(bakery, customerName, itemName, quantity);
                    break;
                case 2:
                    bakeryService.displayInventory(bakery);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
