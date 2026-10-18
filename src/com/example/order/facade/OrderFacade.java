package com.example.order.facade;

import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.model.Customer;
import com.example.order.model.Order;
import com.example.order.subsystem.InventoryService;
import com.example.order.subsystem.NotificationService;
import com.example.order.subsystem.PaymentProcessor;
import com.example.order.subsystem.ShippingService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderFacade {

    private final InventoryService inventory;
    private final PaymentProcessor payment;
    private final ShippingService shipping;
    private final NotificationService notification;

    public OrderFacade() {
        this(new InventoryService(), new PaymentProcessor(),
                new ShippingService(), new NotificationService());
    }

    public OrderFacade(InventoryService inventory,
                       PaymentProcessor payment,
                       ShippingService shipping,
                       NotificationService notification) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
        this.notification = notification;
    }

    public Order placeOrder(Cart cart, Customer customer, String paymentToken) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        String orderId = UUID.randomUUID().toString().substring(0, 8);
        List<CartItem> reserved = new ArrayList<>();

        try {
            for (CartItem item : cart.getItems()) {
                if (!inventory.isAvailable(item.sku(), item.quantity())) {
                    throw new OrderFailedException("Out of stock: " + item.sku());
                }
            }
            for (CartItem item : cart.getItems()) {
                inventory.reserve(item);
                reserved.add(item);
            }

            double total = cart.totalPrice();
            if (!payment.charge(customer, total, paymentToken)) {
                throw new OrderFailedException("Payment declined");
            }

            String tracking = shipping.scheduleDelivery(customer, orderId);
            notification.sendOrderConfirmation(customer, orderId);

            System.out.println("[Facade] Order " + orderId + " placed (tracking " + tracking + ")");
            return new Order(orderId, customer, cart.getItems(), total);

        } catch (OrderFailedException e) {
            System.out.println("[Facade] Rollback: " + e.getMessage());
            rollback(reserved, customer, cart.totalPrice());
            throw e;
        }
    }

    public void cancelOrder(Order order) {
        payment.refund(order.customer(), order.total());
        for (CartItem item : order.items()) {
            inventory.release(item);
        }
        notification.sendCancellation(order.customer(), order.orderId());
        System.out.println("[Facade] Order " + order.orderId() + " cancelled");
    }

    private void rollback(List<CartItem> reserved, Customer customer, double amount) {
        for (CartItem item : reserved) {
            inventory.release(item);
        }
        if (!reserved.isEmpty()) {
            payment.refund(customer, amount);
        }
    }

    public static class OrderFailedException extends RuntimeException {
        public OrderFailedException(String message) {
            super(message);
        }
    }
}