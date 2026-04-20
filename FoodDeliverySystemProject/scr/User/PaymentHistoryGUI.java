package User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Payment.PaymentDAO;

/**
 * PaymentHistoryGUI displays the logged-in user's payment history
 * in a styled Swing interface.
 *
 * <p>This class retrieves payment records from the database using
 * {@link PaymentDAO} and renders them as UI cards.</p>
 *
 * <p>Each payment is displayed with its details and a status indicator.</p>
 *
 * @author
 * @version 1.0
 */
public class PaymentHistoryGUI extends JFrame {

    /** Background color of the application */
    private final Color BG_COLOR = new Color(235, 236, 240);

    /** Card background color for payment entries */
    private final Color CARD_COLOR = new Color(28, 28, 32);

    /** Primary accent color (gold theme) */
    private final Color PRIMARY = new Color(212, 175, 55);

    /** Main text color (white) */
    private final Color TEXT_MAIN = Color.WHITE;

    /** Secondary text color (gray) */
    private final Color TEXT_SECOND = new Color(170, 170, 170);

    /**
     * Constructs the Payment History window.
     *
     * <p>Initializes layout, header, sidebar, and loads
     * all payment records for the current session user.</p>
     */
    public PaymentHistoryGUI() {

        setTitle("Payment History");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG_COLOR);

        // =========================
        // HEADER SECTION
        // =========================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_COLOR);

        JLabel title = new JLabel("Payment History");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // =========================
        // SIDEBAR NAVIGATION
        // =========================
        add(new Sidebar(this), BorderLayout.WEST);

        // =========================
        // MAIN CONTENT PANEL
        // =========================
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(20);

        add(scroll, BorderLayout.CENTER);

        // Load payments from database
        loadPayments(mainPanel);
    }

    /**
     * Loads payment records for the currently logged-in user
     * and renders them as UI cards.
     *
     * @param mainPanel container panel where payment cards are added
     */
    private void loadPayments(JPanel mainPanel) {

        PaymentDAO dao = new PaymentDAO();

        // Retrieve payment history for current session user
        List<String> payments = dao.getPaymentsByUser(Session.userId);

        // Handle empty state
        if (payments == null || payments.isEmpty()) {
            JLabel empty = new JLabel("No payments found");
            empty.setForeground(TEXT_SECOND);
            mainPanel.add(empty);
            return;
        }

        // Render each payment entry
        for (String p : payments) {
            mainPanel.add(createCard(p));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    /**
     * Creates a styled UI card representing a single payment record.
     *
     * @param payment formatted payment string from DAO
     * @return JPanel representing the payment card UI component
     */
    private JPanel createCard(String payment) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Payment text (left side)
        JLabel text = new JLabel(payment);
        text.setForeground(TEXT_MAIN);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Payment status (right side)
        JLabel status = new JLabel("Completed");
        status.setForeground(PRIMARY);

        card.add(text, BorderLayout.WEST);
        card.add(status, BorderLayout.EAST);

        // Hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {

            /**
             * Triggered when the mouse cursor enters the card area.
             * 
             * Purpose:
             * - Provides visual feedback to the user
             * - Highlights the card currently being hovered
             */
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Change background to a slightly lighter shade
                // to indicate active hover state
                card.setBackground(new Color(35, 35, 40));
            }

            /**
             * Triggered when the mouse cursor leaves the card area.
             * 
             * Purpose:
             * - Restores original appearance of the card
             * - Removes hover highlight effect
             */
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Reset background back to default card color
                card.setBackground(CARD_COLOR);
            }
        });

        // Return the fully constructed payment card UI component
        return card;
    }
}