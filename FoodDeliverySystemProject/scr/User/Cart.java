package User;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a simple in-memory shopping cart.
 * Stores items with their quantities and prices.
 */
public class Cart {

    // Stores item name -> quantity
    public static Map<String, Integer> quantities = new HashMap<>();

    // Stores item name -> price
    public static Map<String, Double> prices = new HashMap<>();

    /**
     * Adds an item to the cart.
     * If the item already exists, increases its quantity.
     *
     * @param name  item name
     * @param price item price
     */
    public static void addItem(String name, double price) {

        // Increase quantity if item exists, otherwise set to 1
        quantities.put(name, quantities.getOrDefault(name, 0) + 1);

        // Store/overwrite price (assumes latest price is valid)
        prices.put(name, price);
    }

    /**
     * Removes one quantity of an item from the cart.
     * If quantity becomes 0, removes the item completely.
     *
     * @param name item name
     */
    public static void removeItem(String name) {

        // Check if item exists in cart
        if (quantities.containsKey(name)) {

            int q = quantities.get(name);

            // Reduce quantity or remove item completely
            if (q > 1) {
                quantities.put(name, q - 1);
            } else {
                quantities.remove(name);
                prices.remove(name);
            }
        }
    }

    /**
     * Calculates the subtotal price of all items in the cart.
     *
     * @return total price of all items
     */
    public static double getSubtotal() {

        double total = 0;

        // Multiply price by quantity for each item
        for (String item : quantities.keySet()) {
            total += prices.get(item) * quantities.get(item);
        }

        return total;
    }

    /**
     * Clears all items from the cart.
     */
    public static void clear() {
        quantities.clear();
        prices.clear();
    }
}