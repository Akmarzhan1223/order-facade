package com.example.order.subsystem;
import com.example.order.model.Customer;
public class ShippingService {
    public String scheduleDelivery(Customer customer, String orderId) {
        String tracking = "TRK-" + orderId;
        System.out.println("[Shipping] Scheduled delivery to " + customer.address()
                + " (tracking " + tracking + ")");
        return tracking;
    }
    public void cancelShipment(String trackingNumber) {
        System.out.println("[Shipping] Cancelled shipment " + trackingNumber);
    }
}