package com.example.order.subsystem;
import com.example.order.model.Customer;
public class PaymentProcessor {
    public boolean charge(Customer customer, double amount, String paymentToken) {
        if (paymentToken == null || paymentToken.isBlank()) {
            System.out.println("[Payment] Declined: missing payment token");
            return false;
        }
        if (amount <= 0) {
            System.out.println("[Payment] Declined: invalid amount");
            return false;
        }
        System.out.println("[Payment] Charged " + amount + " to " + customer.name());
        return true;
    }
    public void refund(Customer customer, double amount) {
        System.out.println("[Payment] Refunded " + amount + " to " + customer.name());
    }
}