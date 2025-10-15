package warehouse;

import java.util.HashMap;
import java.util.Map;

class Inventory {
    private Map<Integer, Product> products = new HashMap<>();

    // Add product
    public void addProduct(Product product) {
        products.put(product.getId(), product);
        System.out.println("Product added successfully!");
    }

 // Update product
    public void updateProduct(int id, String name, int qty, double price, int supplierId) {
        Product product = products.get(id);
        if (product != null) {
            product.setName(name);
            product.setQuantity(qty);
            product.setPrice(price);
            product.setSupplierId(supplierId);
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    // Delete product
    public void deleteProduct(int id) {
        if (products.remove(id) != null) {
            System.out.println("Product deleted successfully!");
        } else {
            System.out.println("Product not found!");
        }
    }

    // Search product
    public void searchProduct(int id) {
        Product product = products.get(id);
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Product not found!");
        }
    }

    // List all products
    public void listProducts() {
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
        } else {
            products.values().forEach(System.out::println);
        }
    }
}
