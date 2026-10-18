package com.example.order.subsystem;
import com.example.order.model.Customer;
public class NotificationService {
    public void sendOrderConfirmation(Customer customer, String orderId) {
        System.out.println("[Notify] Confirmation email sent to " + customer.email()
                + " for order " + orderId);
    }
    public void sendCancellation(Customer customer, String orderId) {
        System.out.println("[Notify] Cancellation email sent to " + customer.email()
                + " for order " + orderId);
    }
}
