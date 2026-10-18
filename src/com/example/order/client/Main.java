package com.example.order.client;

import com.example.order.facade.OrderFacade;
import com.example.order.model.Cart;
import com.example.order.model.CartItem;
import com.example.order.model.Customer;
import com.example.order.model.Order;

public class Main {
    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();

        Customer alice = new Customer("C-1", "Alice", "alice@example.com", "Almaty, Abay 10");

        Cart cart1 = new Cart();
        cart1.addItem(new CartItem("SKU-1", "Keyboard", 2, 15000));
        cart1.addItem(new CartItem("SKU-2", "Mouse", 1, 8000));

        Order order1 = facade.placeOrder(cart1, alice, "tok_visa_1234");


        facade.cancelOrder(order1);


        Cart cart2 = new Cart();
        cart2.addItem(new CartItem("SKU-3", "Monitor", 1, 90000));
        try {
            facade.placeOrder(cart2, alice, "tok_visa_1234");
        } catch (OrderFacade.OrderFailedException e) {
            System.out.println("Client caught: " + e.getMessage());
        }


        Cart cart3 = new Cart();
        cart3.addItem(new CartItem("SKU-1", "Keyboard", 1, 15000));
        try {
            facade.placeOrder(cart3, alice, "");
        } catch (OrderFacade.OrderFailedException e) {
            System.out.println("Client caught: " + e.getMessage());
        }
    }
}
