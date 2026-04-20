package MenuItem;

/**
 * Represents a menu item in the online food delivery system.
 * Each item has an ID, name, price, category, and image reference.
 */
public class MenuItem {

    // Unique identifier for the menu item
    private int id;
    // Name of the menu item
    private String name;
    // Price of the menu item
    private double price;
    // Category of the item 
    private String category;
    // Image path or URL representing the menu item
    private String image;

    /**
     * Constructs a MenuItem object with all required fields.
     *
     * @param id the unique ID of the menu item
     * @param name the name of the menu item
     * @param price the price of the menu item
     * @param category the category of the menu item
     * @param image the image path or URL of the menu item
     */
    public MenuItem(int id, String name, double price, String category, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    /**
     * Gets the name of the menu item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the category of the menu item.
     *
     * @return the category of the item
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the image associated with the menu item.
     *
     * @return the image path or URL
     */
    public String getImage() {
        return image;
    }
}