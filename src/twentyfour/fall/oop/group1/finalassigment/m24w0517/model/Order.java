package twentyfour.fall.oop.group1.finalassigment.m24w0517.model;



public class Order {
    private String customerName;
    private String itemName;
    private int quantity;

    public Order(String customerName, String itemName, int quantity) {
        this.customerName = customerName;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
