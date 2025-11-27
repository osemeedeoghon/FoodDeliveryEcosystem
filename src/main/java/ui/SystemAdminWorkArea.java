package ui;

import ecosystem.EcoSystem;
import model.Enterprise;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SystemAdminWorkArea extends JPanel {
    private MainJFrame mainFrame;
    private EcoSystem system;
    private JTable tblEnterprises;
    private JTextField txtName;
    private JComboBox<String> cmbType;

    public SystemAdminWorkArea(MainJFrame mainFrame, EcoSystem system) {
        this.mainFrame = mainFrame;
        this.system = system;
        initComponents();
        populateTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> mainFrame.logout());
        headerPanel.add(new JLabel("System Admin Area"));
        headerPanel.add(btnLogout);
        add(headerPanel, BorderLayout.NORTH);

        // Content
        JTabbedPane tabbedPane = new JTabbedPane();

        // Enterprise Management Tab
        JPanel enterprisePanel = new JPanel(new BorderLayout());

        // Table
        tblEnterprises = new JTable();
        enterprisePanel.add(new JScrollPane(tblEnterprises), BorderLayout.CENTER);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Type:"));
        cmbType = new JComboBox<>(new String[] { "Restaurant", "Delivery" });
        formPanel.add(cmbType);

        JButton btnCreate = new JButton("Create Enterprise");
        btnCreate.addActionListener(e -> createEnterprise());
        formPanel.add(btnCreate);

        enterprisePanel.add(formPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Manage Enterprises", enterprisePanel);

        // Manage Enterprise Admins (Simplified for now)
        JPanel adminPanel = new JPanel();
        adminPanel.add(new JLabel("Manage Enterprise Admins (To be implemented)"));
        tabbedPane.addTab("Manage Admins", adminPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void populateTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Type");

        List<Enterprise> enterprises = system.getAllEnterprises();
        for (Enterprise ent : enterprises) {
            model.addRow(new Object[] { ent.getId(), ent.getName(), ent.getType() });
        }
        tblEnterprises.setModel(model);
    }

    private void createEnterprise() {
        String name = txtName.getText();
        String type = (String) cmbType.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty");
            return;
        }

        system.createEnterprise(name, type);
        populateTable();
        txtName.setText("");
        JOptionPane.showMessageDialog(this, "Enterprise created successfully");
    }
}
