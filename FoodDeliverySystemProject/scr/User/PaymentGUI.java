package User;

import javax.swing.*;
import Order.OrderDAO;
import Order.OrderItemsDAO;
import Payment.PaymentDAO;
import java.awt.*;

/**
 * Payment screen that handles the checkout process.
 * Allows user to select payment method (card or cash),
 * validates input, creates order, and saves payment data.
 */
public class PaymentGUI extends JFrame {

    private JRadioButton cardBtn, cashBtn;
    private JTextField cardField, expiryField, cvvField;
    private JPanel cardPanel;

    // UI theme colors
    private final Color BG = new Color(235, 236, 240);
    private final Color CARD = new Color(28, 28, 32);
    private final Color PRIMARY = new Color(212, 175, 55);
    private final Color TEXT = Color.WHITE;

    // Additional fees
    private final double SERVICE_FEE = 0.99;
    private final double DELIVERY_FEE = 2.99;

    /**
     * Constructor - initializes the payment screen UI.
     */
    public PaymentGUI() {

        setTitle("Payment");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        add(createHeader(), BorderLayout.NORTH);
        add(createBody(), BorderLayout.CENTER);
    }

    /**
     * Creates the top header panel.
     */
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG);

        JLabel title = new JLabel("Payment", SwingConstants.CENTER);
        title.setForeground(PRIMARY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        header.add(title, BorderLayout.CENTER);
        return header;
    }

    /**
     * Builds the main UI content (form + buttons).
     */
    private JPanel createBody() {

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BG);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(420, 450));

        // Calculate total price
        double total = Cart.getSubtotal() + SERVICE_FEE + DELIVERY_FEE;

        JLabel totalLabel = new JLabel("Total: €" + String.format("%.2f", total));
        totalLabel.setForeground(PRIMARY);
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Payment method selection
        cardBtn = new JRadioButton("Card");
        cashBtn = new JRadioButton("Cash");

        styleRadio(cardBtn);
        styleRadio(cashBtn);

        ButtonGroup group = new ButtonGroup();
        group.add(cardBtn);
        group.add(cashBtn);

        JPanel radio = new JPanel();
        radio.setBackground(CARD);
        radio.add(cardBtn);
        radio.add(cashBtn);

        // Card input fields (hidden unless "Card" selected)
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(CARD);
        cardPanel.setVisible(false);

        cardField = createField("Card Number");
        expiryField = createField("Expiry (MM/YY)");
        cvvField = createField("CVV");

        cardPanel.add(cardField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(expiryField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(cvvField);

        JButton payBtn = createButton("Pay");

        // Show/hide card fields depending on selection
        cardBtn.addActionListener(e -> cardPanel.setVisible(true));
        cashBtn.addActionListener(e -> cardPanel.setVisible(false));

        payBtn.addActionListener(e -> processPayment());

        // Layout
        card.add(totalLabel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(radio);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(cardPanel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(payBtn);

        wrapper.add(card);
        return wrapper;
    }

    // =========================
    // VALIDATION
    // =========================

    /**
     * Validates payment input depending on selected method.
     *
     * @param method selected payment method (CARD or CASH)
     * @return true if valid, false otherwise
     */
    private boolean validatePayment(String method) {

        // 1. Check if cart is empty
        if (Cart.quantities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return false;
        }

        // 2. If cash is selected, no further validation needed
        if (method.equals("CASH")) return true;

        // 3. Get and normalize input values
        String cardNumber = cardField.getText().trim().replaceAll("[\\s-]", "");
        String expiry = expiryField.getText().trim();
        String cvv = cvvField.getText().trim();

        // 4. Check for empty fields
        if (cardNumber.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all card details!");
            return false;
        }

        // 5. Card number must be exactly 16 digits
        if (!cardNumber.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, "Card number must be exactly 16 digits!");
            return false;
        }

        // 6. Luhn algorithm validation (checks if card number is realistic)
        if (!isValidCard(cardNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid card number!");
            return false;
        }

        // 7. Expiry date must be in MM/YY format
        if (!expiry.matches("(0[1-9]|1[0-2])\\/\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Invalid expiry format! Use MM/YY (e.g., 12/25)");
            return false;
        }

        // 8. CVV must be 3 or 4 digits
        if (!cvv.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(this, "CVV must be 3 or 4 digits!");
            return false;
        }

        return true;
    }

    /**
     * Validates card number using Luhn algorithm.
     *
     * @param number card number (digits only)
     * @return true if valid, false otherwise
     */
    private boolean isValidCard(String number) {

        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {

            int n = Integer.parseInt(number.substring(i, i + 1));

            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }

            sum += n;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    /**
     * Handles full payment process:
     * validation, order creation, saving items and payment.
     */
    private void processPayment() {

        String method;

        if (cardBtn.isSelected()) {
            method = "CARD";
        } else if (cashBtn.isSelected()) {
            method = "CASH";
        } else {
            JOptionPane.showMessageDialog(this, "Select payment method!");
            return;
        }

        if (!validatePayment(method)) return;

        double total = Cart.getSubtotal() + SERVICE_FEE + DELIVERY_FEE;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Total: €" + total + "\nConfirm?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        OrderDAO orderDAO = new OrderDAO();
        int orderId = orderDAO.createOrder(Session.userId, total);

        if (orderId == -1) {
            JOptionPane.showMessageDialog(this, "Order failed!");
            return;
        }

        OrderItemsDAO itemsDAO = new OrderItemsDAO();
        itemsDAO.saveItems(orderId, Cart.quantities, Cart.prices);

        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.insertPayment(orderId, Session.userId, total, method);

        orderDAO.updateStatus(orderId, "PAID");

        Cart.clear();

        JOptionPane.showMessageDialog(this, "Order placed successfully!");

        new DashboardGUI().setVisible(true);
        dispose();
    }

    /**
     * Creates styled text input field.
     */
    private JTextField createField(String title) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setBorder(BorderFactory.createTitledBorder(title));
        return field;
    }

    /**
     * Styles radio buttons.
     */
    private void styleRadio(JRadioButton btn) {
        btn.setBackground(CARD);
        btn.setForeground(TEXT);
        btn.setFocusPainted(false);
    }

    /**
     * Creates styled button with hover effect.
     */
    private JButton createButton(String text) {

        JButton btn = new JButton(text);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);

        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(230, 190, 70));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(PRIMARY);
            }
        });

        return btn;
    }
}