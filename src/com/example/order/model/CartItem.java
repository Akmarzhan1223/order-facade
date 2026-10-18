package com.example.order.model;
public record CartItem(String sku, String name, int quantity, double price) {
    public double total() {
        return quantity * price;
    }
}