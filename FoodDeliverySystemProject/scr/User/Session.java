package User;

/**
 * Session class used to store global user state
 * during application runtime.
 *
 * This is a simple in-memory session holder.
 * It is not persistent and resets when the application closes.
 */
public class Session {

    // Stores the currently logged-in user's ID
    public static int userId;

    // Stores the currently logged-in user's email
    public static String userEmail;
}