package twentyfour.fall.oop.group1.finalassigment.m24w0517.Service;

import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Inventory;
import twentyfour.fall.oop.group1.finalassigment.m24w0517.model.Order;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileHandler {
    private static final String INVENTORY_FOLDER = "inventory/";
    private static final String INVOICE_FOLDER = "invoices/";

    public FileHandler() {
        // Create folders if they don't exist
        File inventoryFolder = new File(INVENTORY_FOLDER);
        if (!inventoryFolder.exists()) {
            inventoryFolder.mkdir();
        }

        File invoiceFolder = new File(INVOICE_FOLDER);
        if (!invoiceFolder.exists()) {
            invoiceFolder.mkdir();
        }
    }



    public void saveInventoryToFile(List<Inventory> inventoryList, String branchName) {
        String branchFolderPath = INVENTORY_FOLDER + branchName + "/";
        File branchFolder = new File(branchFolderPath);
        if (!branchFolder.exists()) {
            branchFolder.mkdirs();
        }
        String branchFilePath = branchFolderPath + "inventory.txt";
        System.out.println("Saving inventory to path: " + branchFilePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(branchFilePath, false))) {
            for (Inventory item : inventoryList) {
                String line = item.getItemName() + "," + item.getQuantity() + "," + item.getPrice();
                writer.write(line);
                writer.newLine();
                System.out.println("Writing line: " + line); // Debugging each line
            }
            System.out.println("Inventory successfully saved.");
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }


    public List<Inventory> loadInventoryFromFile(String branchName) {
        List<Inventory> inventoryList = new ArrayList<>();
        String branchInventoryFile = INVENTORY_FOLDER + branchName + "/inventory.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(branchInventoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    double price = Double.parseDouble(parts[2]);
                    inventoryList.add(new Inventory(itemName, quantity, price));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
        return inventoryList;
    }

    public void saveOrderToFile(Order order, double totalPrice, String branchName) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String branchInvoiceFolderPath = INVOICE_FOLDER + branchName + "/";
        File branchInvoiceFolder = new File(branchInvoiceFolderPath);
        if (!branchInvoiceFolder.exists()) {
            branchInvoiceFolder.mkdirs();
        }

        String fileName = branchInvoiceFolderPath + "Invoice_" + order.getCustomerName() + "_" + timestamp + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Invoice for " + order.getCustomerName() + "\n");
            writer.write("Item: " + order.getItemName() + "\n");
            writer.write("Quantity: " + order.getQuantity() + "\n");
            writer.write(String.format("Total Price: %.2f\n", totalPrice));
            writer.write("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n");
        } catch (IOException e) {
            System.out.println("Error saving order invoice: " + e.getMessage());
        }
    }


    public void displayAllInvoices(String branchName) {
        String branchInvoiceFolderPath = INVOICE_FOLDER + branchName + "/";
        File branchInvoiceFolder = new File(branchInvoiceFolderPath);

        if (!branchInvoiceFolder.exists()) {
            System.out.println("No invoices found for branch: " + branchName);
            return;
        }

        File[] invoiceFiles = branchInvoiceFolder.listFiles((dir, name) -> name.endsWith(".txt"));
        if (invoiceFiles == null || invoiceFiles.length == 0) {
            System.out.println("No invoices found for branch: " + branchName);
            return;
        }

        System.out.println("Invoices for branch: " + branchName);
        for (File invoiceFile : invoiceFiles) {
            System.out.println("Reading file: " + invoiceFile.getName());
            try (BufferedReader reader = new BufferedReader(new FileReader(invoiceFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("--------------------------------------------------");
            } catch (IOException e) {
                System.out.println("Error reading invoice file: " + invoiceFile.getName() + " - " + e.getMessage());
            }
        }
    }

}
