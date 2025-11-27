package ui;

import ecosystem.EcoSystem;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RestaurantManagerWorkArea extends JPanel {
    private MainJFrame mainFrame;
    private EcoSystem system;
    private JTable tblOrders;

    public RestaurantManagerWorkArea(MainJFrame mainFrame, EcoSystem system) {
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
        headerPanel.add(new JLabel("Restaurant Manager Area"));
        headerPanel.add(btnLogout);
        add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Orders Tab
        JPanel ordersPanel = new JPanel(new BorderLayout());
        tblOrders = new JTable();
        ordersPanel.add(new JScrollPane(tblOrders), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel();
        JButton btnAccept = new JButton("Accept Order");
        btnAccept.addActionListener(e -> updateStatus("Accepted"));
        JButton btnReady = new JButton("Ready for Pickup");
        btnReady.addActionListener(e -> updateStatus("ReadyForPickup"));

        actionPanel.add(btnAccept);
        actionPanel.add(btnReady);
        ordersPanel.add(actionPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Manage Orders", ordersPanel);

        // Menu Tab
        JPanel menuPanel = new JPanel();
        menuPanel.add(new JLabel("Manage Menu (To be implemented)"));
        tabbedPane.addTab("Manage Menu", menuPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void populateOrders() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order ID");
        model.addColumn("Customer");
        model.addColumn("Status");
        model.addColumn("Date");

        // Assuming manager is linked to an organization via User.organizationId
        // But for now, let's assume we pass the restaurant ID somehow or fetch it.
        // For simplicity, let's assume the user's organizationId IS the restaurantId.
        int restaurantId = system.getCurrentUser().getOrganizationId();

        List<Order> orders = system.getOrdersForRestaurant(restaurantId);
        for (Order o : orders) {
            model.addRow(new Object[] { o.getId(), o.getCustomerId(), o.getStatus(), o.getOrderDate() });
        }
        tblOrders.setModel(model);
    }

    private void updateStatus(String status) {
        int selectedRow = tblOrders.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = (int) tblOrders.getValueAt(selectedRow, 0);
            system.updateOrderStatus(orderId, status, 0);
            populateOrders();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an order");
        }
    }
}
