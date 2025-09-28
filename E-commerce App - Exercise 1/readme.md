# E-Commerce Application

This is a simple console-based e-commerce application built in Java. It allows users to browse products, add them to a shopping cart, and complete purchases with various payment and shipping options. The application demonstrates the use of several design patterns to create a clean and maintainable codebase.

## Features

- **Product Listing**: View all available products with their details including name, price, and stock levels.
- **Shopping Cart**: Add products to your cart with specified quantities. The cart keeps track of items and calculates subtotals.
- **Checkout Process**: Complete your order by selecting payment methods (Credit Card or UPI) and shipping options (Standard, Express, or Free shipping based on order value).
- **Order Notifications**: Receive email and SMS notifications when an order is completed.
- **Stock Management**: Products have stock levels that decrease when items are added to the cart.

## Design Patterns Used

This application incorporates the following design patterns:

- **Facade Pattern**: The `ECommerceFacade` class provides a simplified interface to the complex subsystem of products, cart, and orders.
- **Builder Pattern**: The `OrderBuilder` class is used to construct `Order` objects step by step, allowing for flexible order creation.
- **Observer Pattern**: Order status changes notify observers like `EmailNotifier` and `SMSNotifier`.
- **Strategy Pattern**: Different shipping strategies (`StandardShipping`, `ExpressShipping`, `FreeShipping`) implement the `ShippingStrategy` interface.
- **Factory Pattern**: The `PaymentFactory` creates appropriate payment processors based on the selected payment type.

## How to Run

1. Ensure you have Java installed on your system.
2. Navigate to the directory containing the Java files.
3. Compile the application:
   ```
   javac *.java
   ```
4. Run the application:
   ```
   java App
   ```

## Usage

The application presents a menu with the following options:

1. **List Products**: Displays all available products.
2. **Add To Cart**: Prompts for a product ID and quantity to add to the cart.
3. **View Cart**: Shows the current contents of the cart and subtotal.
4. **Checkout**: Guides you through selecting payment method, providing payment details, and choosing shipping. Completes the order and clears the cart.
5. **Exit**: Closes the application.

Follow the on-screen prompts to navigate through the options. Make sure to enter valid product IDs and quantities to avoid errors.

## Sample Products

The application comes pre-loaded with some sample products for demonstration purposes. You can view them by selecting option 1 from the menu.

## Notes

- Payment processing is simulated and does not connect to real payment gateways.
- Notifications are printed to the console for simplicity.
- The application runs in a loop until you choose to exit.
- Stock levels are updated in real-time as items are added to carts.

This application serves as an educational example of implementing design patterns in a practical e-commerce scenario.
