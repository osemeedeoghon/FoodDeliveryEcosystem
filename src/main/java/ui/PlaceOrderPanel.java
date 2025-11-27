package ui;

import ecosystem.EcoSystem;
import model.Enterprise;
import model.Order;
import model.Organization;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaceOrderPanel extends JPanel {
    private MainJFrame mainFrame;
    private EcoSystem system;
    private JComboBox<String> cmbRestaurants;
    private JTextArea txtComment;
    private JTextField txtAddress;

    public PlaceOrderPanel(MainJFrame mainFrame, EcoSystem system) {
        this.mainFrame = mainFrame;
        this.system = system;
        initComponents();
        populateRestaurants();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBack = new JButton("<< Back");
        btnBack.addActionListener(e -> mainFrame.navigateTo(new CustomerWorkArea(mainFrame, system), "Customer"));
        headerPanel.add(btnBack);
        headerPanel.add(new JLabel("Place Order"));
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2));

        formPanel.add(new JLabel("Select Restaurant:"));
        cmbRestaurants = new JComboBox<>();
        formPanel.add(cmbRestaurants);

        formPanel.add(new JLabel("Delivery Address:"));
        txtAddress = new JTextField();
        formPanel.add(txtAddress);

        formPanel.add(new JLabel("Order Details / Comment:"));
        txtComment = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(txtComment));

        JButton btnPlace = new JButton("Place Order");
        btnPlace.addActionListener(e -> placeOrder());
        formPanel.add(btnPlace);

        add(formPanel, BorderLayout.CENTER);
    }

    private void populateRestaurants() {
        // For simplicity, we are listing Enterprises of type 'Restaurant'
        // In a real app, we might list Organizations or specific Restaurant objects
        List<Enterprise> enterprises = system.getAllEnterprises();
        for (Enterprise ent : enterprises) {
            if ("Restaurant".equalsIgnoreCase(ent.getType())) {
                cmbRestaurants.addItem(ent.getName() + " (ID: " + ent.getId() + ")");
            }
        }
    }

    private void placeOrder() {
        String restaurantStr = (String) cmbRestaurants.getSelectedItem();
        String address = txtAddress.getText();
        String comment = txtComment.getText();

        if (restaurantStr == null || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        // Parse ID from string "Name (ID: 1)"
        int restaurantId = Integer
                .parseInt(restaurantStr.substring(restaurantStr.lastIndexOf("ID: ") + 4, restaurantStr.length() - 1));

        Order order = new Order();
        order.setCustomerId(system.getCurrentUser().getId());
        order.setRestaurantId(restaurantId);
        order.setStatus("Placed");
        order.setDeliveryAddress(address);
        order.setComment(comment);

        system.placeOrder(order);

        JOptionPane.showMessageDialog(this, "Order placed successfully!");
        mainFrame.navigateTo(new CustomerWorkArea(mainFrame, system), "Customer");
    }
}
