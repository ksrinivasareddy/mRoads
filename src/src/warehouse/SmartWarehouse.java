package warehouse;

import java.util.Scanner;

public class SmartWarehouse {
    private static Inventory inventory = new Inventory();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- SmartWarehouse Menu ---");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Search Product");
            System.out.println("5. List All Products");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addProductMenu();
                case 2 -> updateProductMenu();
                case 3 -> deleteProductMenu();
                case 4 -> searchProductMenu();
                case 5 -> inventory.listProducts();
                case 6 -> exit = true;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void addProductMenu() {
        System.out.print("Enter Product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();
        System.out.print("Enter Unit Price: "); // updated
        double unitPrice = scanner.nextDouble();
        System.out.print("Enter Supplier ID: ");
        int supplierId = scanner.nextInt();
        scanner.nextLine();

        Product product = new Product(id, name, qty, unitPrice, supplierId);
        inventory.addProduct(product);
    }

    private static void updateProductMenu() {
        System.out.print("Enter Product ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Quantity: ");
        int qty = scanner.nextInt();
        System.out.print("Enter New Unit Price: "); // updated
        double unitPrice = scanner.nextDouble();
        System.out.print("Enter New Supplier ID: ");
        int supplierId = scanner.nextInt();
        scanner.nextLine();

        inventory.updateProduct(id, name, qty, unitPrice, supplierId);
    }


    private static void deleteProductMenu() {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        inventory.deleteProduct(id);
    }

    private static void searchProductMenu() {
        System.out.print("Enter Product ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        inventory.searchProduct(id);
    }
}
