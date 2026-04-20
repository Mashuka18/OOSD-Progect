package Restaurant;

/**
 * Represents a restaurant in the online food delivery system.
 * Stores basic information such as name, description, rating, and image.
 */
public class Restaurant {

    // Unique identifier for the restaurant
    private int id;

    // Restaurant name (e.g., Dominos, McDonalds)
    private String name;

    // Short description of the restaurant
    private String description;

    // Rating of the restaurant (e.g., 4.5 stars)
    private double rating;

    // Image path or URL of the restaurant
    private String image;

    /**
     * Constructs a Restaurant object with all required fields.
     *
     * @param id the unique restaurant ID
     * @param name the name of the restaurant
     * @param description the description of the restaurant
     * @param rating the rating of the restaurant
     * @param image the image path or URL
     */
    public Restaurant(int id, String name, String description, double rating, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.image = image;
    }

    /**
     * Gets the restaurant name.
     *
     * @return the name of the restaurant
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the restaurant description.
     *
     * @return the description of the restaurant
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the restaurant rating.
     *
     * @return the rating value
     */
    public double getRating() {
        return rating;
    }

    /**
     * Gets the restaurant image path or URL.
     *
     * @return the image of the restaurant
     */
    public String getImage() {
        return image;
    }
}