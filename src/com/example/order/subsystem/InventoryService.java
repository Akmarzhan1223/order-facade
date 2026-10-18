package com.example.order.subsystem;
import com.example.order.model.CartItem;
import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private final Map<String, Integer> stock = new HashMap<>();
    public InventoryService() {
        stock.put("SKU-1", 10);
        stock.put("SKU-2", 5);
        stock.put("SKU-3", 0);
    }
    public boolean isAvailable(String sku, int quantity) {
        return stock.getOrDefault(sku, 0) >= quantity;
    }
    public boolean reserve(CartItem item) {
        int available = stock.getOrDefault(item.sku(), 0);
        if (available < item.quantity()) {
            return false;
        }
        stock.put(item.sku(), available - item.quantity());
        System.out.println("[Inventory] Reserved " + item.quantity() + " x " + item.sku());
        return true;
    }
    public void release(CartItem item) {
        stock.merge(item.sku(), item.quantity(), Integer::sum);
        System.out.println("[Inventory] Released " + item.quantity() + " x " + item.sku());
    }
}