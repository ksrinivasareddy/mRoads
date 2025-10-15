package warehouse;

import java.util.Random;

public class WarehouseWorker implements Runnable {
    private Inventory inventory;
    private Random rand = new Random();
    private boolean running = true;

    public WarehouseWorker(Inventory inventory) { this.inventory = inventory; }

    @Override
    public void run() {
        while (running) {
            try {
                if (inventory.getProducts().isEmpty()) { Thread.sleep(1000); continue; }
                Product p = inventory.getProducts().stream().skip(rand.nextInt(inventory.getProducts().size())).findFirst().orElse(null);
                if (p == null) continue;
                int qty = rand.nextInt(3)+1;
                inventory.fulfillOrder(p.getId(), qty);
                Thread.sleep(1000);
            } catch (ProductNotFoundException | InsufficientStockException e) {
                System.out.println("Worker error: " + e.getMessage());
            } catch (InterruptedException e) { running = false; }
        }
    }

    public void stopWorker() { running = false; }
}
