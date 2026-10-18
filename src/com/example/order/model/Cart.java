package com.example.order.model;

import java.util.ArrayList;
import java.util.List;
public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        items.add(item);
    }

    public List<CartItem> getItems() {
        return List.copyOf(items);
    }

    public double totalPrice() {
        return items.stream().mapToDouble(CartItem::total).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}