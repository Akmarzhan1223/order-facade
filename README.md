# Order Facade — Assignment #4

Implementation of the **Facade** structural design pattern in Java (JDK 17) for an Online Order Processing system.

## The Problem
Placing an order requires coordinating several independent subsystems (inventory check, payment processing, shipping, and notifications) in a strict order. If a step fails, previously completed steps must be rolled back. The client should not have to orchestrate all this logic manually.

## The Solution
The `OrderFacade` class provides a simple API (`placeOrder`, `cancelOrder`) that hides the complex interactions between the subsystems.

## Project Structure
- `model/` — Domain objects (`Cart`, `Customer`, `Order`, `CartItem`)
- `subsystem/` — Complex subsystem classes:
  - `InventoryService` (stock management)
  - `PaymentProcessor` (charges and refunds)
  - `ShippingService` (delivery scheduling)
  - `NotificationService` (emails)
- `facade/` — `OrderFacade` (the single entry point coordinating the subsystems)
- `client/` — `Main` (demonstrates usage, uses **only** the facade)

## How to Run
Compile and run from the terminal at the root of the project:
```bash
javac -d out $(find src -name "*.java")
java -cp out com.example.order.client.Main
