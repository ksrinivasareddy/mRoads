package warehouse;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double unitPrice; // price per unit
    private int supplierId;

    public Product(int id, String name, int quantity, double unitPrice, int supplierId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.supplierId = supplierId;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public int getSupplierId() { return supplierId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public double getTotalPrice() {
        return unitPrice * quantity; // total cost = unitPrice × quantity
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Qty: " + quantity +
               ", Unit Price: $" + unitPrice + ", Total Price: $" + getTotalPrice() +
               ", Supplier ID: " + supplierId;
    }
}
