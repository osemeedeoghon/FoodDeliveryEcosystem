package ui;

import ecosystem.EcoSystem;
import model.Order;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerWorkArea extends JPanel {
    private MainJFrame mainFrame;
    private EcoSystem system;
    private JTable tblOrders;

    public CustomerWorkArea(MainJFrame mainFrame, EcoSystem system) {
        this.mainFrame = mainFrame;
        this.system = system;
        initComponents();
        populateOrders();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> mainFrame.logout());
        headerPanel.add(new JLabel("Welcome " + system.getCurrentUser().getName()));
        headerPanel.add(btnLogout);
        add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());

        tblOrders = new JTable();
        contentPanel.add(new JScrollPane(tblOrders), BorderLayout.CENTER);

        JButton btnNewOrder = new JButton("Place New Order");
        // btnNewOrder.addActionListener(e -> mainFrame.navigateTo(new
        // PlaceOrderPanel(mainFrame, system), "PlaceOrder"));
        btnNewOrder.addActionListener(e -> mainFrame.navigateTo(new PlaceOrderPanel(mainFrame, system), "PlaceOrder"));

        contentPanel.add(btnNewOrder, BorderLayout.SOUTH);

        add(contentPanel, BorderLayout.CENTER);
    }

    private void populateOrders() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Restaurant");
        model.addColumn("Status");
        model.addColumn("Date");

        List<Order> orders = system.getOrdersForCustomer(system.getCurrentUser().getId());
        for (Order o : orders) {
            model.addRow(new Object[] { o.getId(), o.getRestaurantId(), o.getStatus(), o.getOrderDate() });
        }
        tblOrders.setModel(model);
    }
}
