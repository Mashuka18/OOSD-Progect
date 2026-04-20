package User;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class for displaying custom styled dialog windows.
 * Supports info, error, and confirmation dialogs.
 */
public class CustomDialog {

    // Theme colors
    private static final Color BG = new Color(28, 28, 32);
    private static final Color PRIMARY = new Color(212, 175, 55);
    private static final Color TEXT = Color.WHITE;
    private static final Color TEXT_SECOND = new Color(170, 170, 170);

    /**
     * Displays an informational dialog.
     *
     * @param parent parent component
     * @param message message to display
     */
    public static void showInfo(Component parent, String message) {
        showDialog(parent, "Info", message, "OK", null);
    }

    /**
     * Displays an error dialog.
     *
     * @param parent parent component
     * @param message error message
     */
    public static void showError(Component parent, String message) {
        showDialog(parent, "Error", message, "OK", null);
    }

    /**
     * Displays a confirmation dialog.
     *
     * @param parent parent component
     * @param message confirmation message
     * @return true if user clicked YES, otherwise false
     */
    public static boolean confirm(Component parent, String message) {

        final boolean[] result = {false};

        JDialog dialog = createBaseDialog(parent, "Confirm");

        JPanel panel = createPanel();
        JLabel text = createText(message);

        JButton yes = createPrimaryButton("Yes");
        JButton no = createSecondaryButton("No");

        yes.addActionListener(e -> {
            result[0] = true;
            dialog.dispose();
        });

        no.addActionListener(e -> {
            result[0] = false;
            dialog.dispose();
        });

        JPanel btns = new JPanel();
        btns.setBackground(BG);
        btns.add(yes);
        btns.add(no);

        panel.add(text);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btns);

        dialog.add(panel);
        dialog.setVisible(true);

        return result[0];
    }

    /**
     * Base method for showing simple dialogs.
     *
     * @param parent parent component
     * @param title dialog title
     * @param message message text
     * @param btnText button text
     * @param action optional action on click
     */
    private static void showDialog(Component parent, String title, String message, String btnText, Runnable action) {

        JDialog dialog = createBaseDialog(parent, title);

        JPanel panel = createPanel();
        JLabel text = createText(message);
        JButton btn = createPrimaryButton(btnText);

        btn.addActionListener(e -> {
            dialog.dispose();
            if (action != null) action.run();
        });

        panel.add(text);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    /**
     * Creates base dialog window.
     */
    private static JDialog createBaseDialog(Component parent, String title) {

        JDialog dialog = new JDialog(
                (Frame) SwingUtilities.getWindowAncestor(parent),
                title,
                true
        );

        dialog.setUndecorated(true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(BG);

        return dialog;
    }

    /**
     * Creates styled panel container.
     */
    private static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    /**
     * Creates styled message label.
     */
    private static JLabel createText(String msg) {
        JLabel label = new JLabel("<html><center>" + msg + "</center></html>");
        label.setForeground(TEXT_SECOND);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Creates primary action button.
     */
    private static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        return btn;
    }

    /**
     * Creates secondary action button.
     */
    private static JButton createSecondaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(50, 50, 55));
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
        return btn;
    }
}